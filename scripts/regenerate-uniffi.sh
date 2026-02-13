#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
RUST_DIR="${ROOT_DIR}/../privchat-rust"
KMP_BINDGEN_DEFAULT="${ROOT_DIR}/../uniffi-kotlin-multiplatform-bindings/target/release/uniffi-bindgen-kotlin-multiplatform"
KMP_BINDGEN="${KMP_BINDGEN:-$KMP_BINDGEN_DEFAULT}"

if [[ ! -x "$KMP_BINDGEN" ]]; then
  echo "[regen] missing bindgen binary: $KMP_BINDGEN"
  echo "[regen] build it first in uniffi-kotlin-multiplatform-bindings"
  exit 1
fi

echo "[regen] build rust ffi dylib"
cd "$RUST_DIR"
cargo build -p privchat-sdk-ffi --release

OUT_DIR="$(mktemp -d /tmp/privchat-uniffi-gen-XXXXXX)"
echo "[regen] generate bindings -> $OUT_DIR"
"$KMP_BINDGEN" \
  --library \
  --crate privchat_sdk_ffi \
  --config "$RUST_DIR/crates/privchat-sdk-ffi/uniffi.toml" \
  --out-dir "$OUT_DIR" \
  "$RUST_DIR/target/release/libprivchat_sdk_ffi.dylib"

echo "[regen] install generated kotlin/headers"
cd "$ROOT_DIR"
FILES=(
  "shared/src/commonMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.common.kt"
  "shared/src/nativeMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.native.kt"
  "shared/src/androidMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.android.kt"
  "shared/src/nativeInterop/cinterop/privchat_sdk_ffi.h"
)
BACKUP_DIR="$(mktemp -d /tmp/privchat-uniffi-backup-XXXXXX)"
for f in "${FILES[@]}"; do
  cp "$f" "$BACKUP_DIR/$(basename "$f")"
done

restore_backup() {
  cp "$BACKUP_DIR/privchat_sdk_ffi.common.kt" "shared/src/commonMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.common.kt"
  cp "$BACKUP_DIR/privchat_sdk_ffi.native.kt" "shared/src/nativeMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.native.kt"
  cp "$BACKUP_DIR/privchat_sdk_ffi.android.kt" "shared/src/androidMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.android.kt"
  cp "$BACKUP_DIR/privchat_sdk_ffi.h" "shared/src/nativeInterop/cinterop/privchat_sdk_ffi.h"
}

install -m 0644 "$OUT_DIR/commonMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.common.kt" \
  "shared/src/commonMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.common.kt"
install -m 0644 "$OUT_DIR/nativeMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.native.kt" \
  "shared/src/nativeMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.native.kt"
install -m 0644 "$OUT_DIR/androidMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.android.kt" \
  "shared/src/androidMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.android.kt"
install -m 0644 "$OUT_DIR/nativeInterop/cinterop/headers/privchat_sdk_ffi/privchat_sdk_ffi.h" \
  "shared/src/nativeInterop/cinterop/privchat_sdk_ffi.h"

echo "[regen] verify kotlin compile"
if ! ./gradlew :shared:compileDebugKotlinAndroid :shared:compileKotlinIosSimulatorArm64 --no-daemon; then
  echo "[regen] generated bindings do not compile with current runtime/templates, rolling back"
  restore_backup
  exit 1
fi

echo "[regen] done"
