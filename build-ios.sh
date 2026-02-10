#!/usr/bin/env bash
# iOS 构建脚本（默认增量快速模式）
# 默认: 仅构建 iOS Simulator 所需内容（用于日常调试，尽量复用缓存）
# 全量: 使用 --full 执行完整发布链路

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

PRIVCHAT_SDK="${PRIVCHAT_SDK:-$(cd "$SCRIPT_DIR/../privchat-rust" 2>/dev/null && pwd)}"
SKIP_RUST=false
FULL=false
WITH_DEVICE=false
RUN_POD=false
FORCE_FRAMEWORK=false

for arg in "$@"; do
  case "$arg" in
    --skip-rust) SKIP_RUST=true ;;
    --full) FULL=true ;;
    --with-device) WITH_DEVICE=true ;;
    --pod-install) RUN_POD=true ;;
    --force-framework) FORCE_FRAMEWORK=true ;;
    -h|--help)
      echo "用法: $0 [--skip-rust] [--full] [--with-device] [--pod-install] [--force-framework]"
      echo "  默认(增量快速): Rust 仅编 ios-sim + Kotlin 仅编模拟器 framework + 不执行 pod install"
      echo "  --skip-rust    跳过 Rust，仅编 Kotlin framework"
      echo "  --full         全量模式：clean + host dylib + 头文件 + ios-sim + ios + gradle clean"
      echo "  --with-device  快速模式下额外编译 iOS 真机 .a 和 framework"
      echo "  --pod-install  结束后执行 sample/iosApp/pod install"
      echo "  --force-framework 强制删除并重建 framework（排查缓存/链接问题时用）"
      exit 0
      ;;
    *)
      echo "未知参数: $arg"
      exit 1
      ;;
  esac
done

if [ -z "$PRIVCHAT_SDK" ] || [ ! -d "$PRIVCHAT_SDK" ]; then
  echo "错误: 未找到 privchat-rust 目录（当前查找: $SCRIPT_DIR/../privchat-rust）"
  echo "可设置环境变量: PRIVCHAT_SDK=/path/to/privchat-rust"
  exit 1
fi

export MACOSX_DEPLOYMENT_TARGET=11.0
BUILD_TS="${BUILD_TIMESTAMP:-}"
run_cargo_build() {
  if [ -n "$BUILD_TS" ]; then
    (cd "$PRIVCHAT_SDK" && BUILD_TIMESTAMP="$BUILD_TS" RUSTC_WRAPPER= cargo "$@")
  else
    (cd "$PRIVCHAT_SDK" && RUSTC_WRAPPER= cargo "$@")
  fi
}
RUST_SIM_LIB="$PRIVCHAT_SDK/target/aarch64-apple-ios-sim/release/libprivchat_sdk_ffi.a"
FRAMEWORK_BIN="$SCRIPT_DIR/sample/build/cocoapods/framework/sample.framework/sample"
SWIFT_BINDINGS_DIR="$PRIVCHAT_SDK/crates/privchat-sdk-ffi/bindings/swift"
SWIFT_FFI_HEADER="$SWIFT_BINDINGS_DIR/PrivchatSDKFFI.h"
KMP_BINDGEN_BIN="${KMP_BINDGEN_BIN:-$SCRIPT_DIR/../uniffi-kotlin-multiplatform-bindings/target/release/uniffi-bindgen-kotlin-multiplatform}"

if [ -n "${JAVA_HOME:-}" ] && [ ! -d "$JAVA_HOME" ]; then
  if command -v /usr/libexec/java_home >/dev/null 2>&1; then
    export JAVA_HOME="$(/usr/libexec/java_home)"
    echo "检测到无效 JAVA_HOME，已自动切换为: $JAVA_HOME"
  fi
fi

# 避免写入 ~/.gradle 在受限环境下触发 .zip.lck 权限错误
export GRADLE_USER_HOME="${GRADLE_USER_HOME:-$SCRIPT_DIR/.gradle-home}"
mkdir -p "$GRADLE_USER_HOME"

echo "========== 1. Rust FFI =========="
if [ -n "$BUILD_TS" ]; then
  echo "BUILD_TIMESTAMP=$BUILD_TS"
else
  echo "BUILD_TIMESTAMP=<unchanged>"
fi
if [ "$SKIP_RUST" = true ]; then
  echo "跳过 Rust（--skip-rust）"
else
  if [ "$FULL" = true ]; then
    echo "全量模式: clean + host dylib + 头文件 + ios-sim + ios"
    (cd "$PRIVCHAT_SDK" && RUSTC_WRAPPER= cargo clean -p privchat-sdk-ffi -p privchat-sdk)
    run_cargo_build build -p privchat-sdk-ffi --release
    (cd "$PRIVCHAT_SDK/crates/privchat-sdk-ffi" && cargo run --manifest-path uniffi-bindgen/Cargo.toml --release -- \
      generate ../../target/release/libprivchat_sdk_ffi.dylib \
      --language swift --out-dir bindings/swift --config uniffi.toml)
    run_cargo_build build -p privchat-sdk-ffi --release --target aarch64-apple-ios-sim
    run_cargo_build build -p privchat-sdk-ffi --release --target aarch64-apple-ios
    ./gradlew clean
  else
    echo "快速模式: 仅编译 ios-sim Rust 静态库"
    run_cargo_build build -p privchat-sdk-ffi --release --target aarch64-apple-ios-sim
    # 快速模式下，若 FFI 源码更新但头文件未更新，则自动重生 Swift FFI 头，避免 cinterop ABI 漂移
    NEED_REBIND=false
    if [ ! -f "$SWIFT_FFI_HEADER" ]; then
      NEED_REBIND=true
    elif find "$PRIVCHAT_SDK/crates/privchat-sdk-ffi/src" -type f \( -name '*.rs' -o -name '*.udl' -o -name '*.toml' \) -newer "$SWIFT_FFI_HEADER" | grep -q .; then
      NEED_REBIND=true
    fi

    if [ "$NEED_REBIND" = true ]; then
      echo "检测到 FFI 源码更新，快速模式补充生成 Swift 头文件"
      run_cargo_build build -p privchat-sdk-ffi --release
      (cd "$PRIVCHAT_SDK/crates/privchat-sdk-ffi" && cargo run --manifest-path uniffi-bindgen/Cargo.toml --release -- \
        generate ../../target/release/libprivchat_sdk_ffi.dylib \
        --language swift --out-dir bindings/swift --config uniffi.toml)

      # 同步重生成 Kotlin Multiplatform native/common 绑定，确保删除旧 FFI 方法后不会残留链接符号引用。
      if [ -x "$KMP_BINDGEN_BIN" ]; then
        echo "检测到 KMP bindgen，自动刷新 shared/src 下 UniFFI 生成代码"
        KMP_OUT_DIR="$(mktemp -d /tmp/privchat-uniffi-gen-XXXXXX)"
        (
          cd "$PRIVCHAT_SDK"
          "$KMP_BINDGEN_BIN" \
            --library \
            --crate privchat_sdk_ffi \
            --config "$PRIVCHAT_SDK/crates/privchat-sdk-ffi/uniffi.toml" \
            --out-dir "$KMP_OUT_DIR" \
            "$PRIVCHAT_SDK/target/release/libprivchat_sdk_ffi.dylib"
        )
        install -m 0644 "$KMP_OUT_DIR/commonMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.common.kt" \
          "$SCRIPT_DIR/shared/src/commonMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.common.kt"
        install -m 0644 "$KMP_OUT_DIR/nativeMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.native.kt" \
          "$SCRIPT_DIR/shared/src/nativeMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.native.kt"
        install -m 0644 "$KMP_OUT_DIR/androidMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.android.kt" \
          "$SCRIPT_DIR/shared/src/androidMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.android.kt"
        install -m 0644 "$KMP_OUT_DIR/nativeInterop/cinterop/headers/privchat_sdk_ffi/privchat_sdk_ffi.h" \
          "$SCRIPT_DIR/shared/src/nativeInterop/cinterop/privchat_sdk_ffi.h"
      else
        echo "警告: 未找到 KMP bindgen 可执行文件，跳过 native/common 绑定刷新: $KMP_BINDGEN_BIN"
      fi
    fi

    if [ "$WITH_DEVICE" = true ]; then
      echo "快速模式 + 真机: 编译 ios arm64 静态库"
      run_cargo_build build -p privchat-sdk-ffi --release --target aarch64-apple-ios
    fi
  fi
fi

# Rust 库比现有 framework 新时，自动切到强制重链，避免 iOS 继续跑旧静态库
if [ "$SKIP_RUST" = false ] && [ -f "$RUST_SIM_LIB" ] && [ -f "$FRAMEWORK_BIN" ] && [ "$RUST_SIM_LIB" -nt "$FRAMEWORK_BIN" ]; then
  FORCE_FRAMEWORK=true
  echo "检测到 Rust 静态库更新，自动启用 --force-framework 以刷新 iOS 链接产物"
fi

echo ""
echo "========== 2. Kotlin Framework =========="
./gradlew :sample:generateDummyFramework

SYNC_FLAGS=()
if [ "$FORCE_FRAMEWORK" = true ]; then
  echo "强制模式: 清理旧 framework 并禁用 Gradle 缓存"
  rm -rf shared/build/classes/kotlin/iosSimulatorArm64/main/cinterop/shared-cinterop-privchat_ffi*
  if [ -d "sample/build/cocoapods/framework/sample.framework" ]; then
    rm -rf sample/build/cocoapods/framework/sample.framework
  fi
  SYNC_FLAGS+=(--rerun-tasks --no-build-cache)
fi

echo "编译 iOS 模拟器 framework"
if [ "${#SYNC_FLAGS[@]}" -gt 0 ]; then
  ./gradlew :sample:syncFramework -Pkotlin.native.cocoapods.platform=iphonesimulator -Pkotlin.native.cocoapods.archs=arm64 -Pkotlin.native.cocoapods.configuration=Release "${SYNC_FLAGS[@]}"
else
  ./gradlew :sample:syncFramework -Pkotlin.native.cocoapods.platform=iphonesimulator -Pkotlin.native.cocoapods.archs=arm64 -Pkotlin.native.cocoapods.configuration=Release
fi

if [ "$FULL" = true ] || [ "$WITH_DEVICE" = true ]; then
  echo "编译 iOS 真机 framework"
  if [ "${#SYNC_FLAGS[@]}" -gt 0 ]; then
    ./gradlew :sample:syncFramework -Pkotlin.native.cocoapods.platform=iphoneos -Pkotlin.native.cocoapods.archs=arm64 -Pkotlin.native.cocoapods.configuration=Release "${SYNC_FLAGS[@]}"
  else
    ./gradlew :sample:syncFramework -Pkotlin.native.cocoapods.platform=iphoneos -Pkotlin.native.cocoapods.archs=arm64 -Pkotlin.native.cocoapods.configuration=Release
  fi
fi

if [ "$RUN_POD" = true ] || [ "$FULL" = true ]; then
  echo ""
  echo "========== 3. CocoaPods =========="
  (cd sample/iosApp && pod install)
fi

echo ""
echo "========== 完成 =========="
echo "默认用于调试：已输出可供 iOS Simulator 使用的最新 FFI + framework"
echo "工作区: $SCRIPT_DIR/sample/iosApp/iosApp.xcworkspace"

# 打印 Rust 库与 framework 的内嵌构建时间，快速验证是否已链接最新产物
if command -v strings >/dev/null 2>&1; then
  RUST_TS="$(strings "$RUST_SIM_LIB" 2>/dev/null | grep -Eo '[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]+)?Z' | head -n 1 || true)"
  FW_TS="$(strings "$FRAMEWORK_BIN" 2>/dev/null | grep -Eo '[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]+)?Z' | head -n 1 || true)"
  echo "Rust lib timestamp: ${RUST_TS:-<not found>}"
  echo "Framework timestamp: ${FW_TS:-<not found>}"
fi
