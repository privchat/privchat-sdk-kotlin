#!/usr/bin/env bash
# Regenerate the Kotlin Multiplatform UniFFI bindings for privchat-sdk-ffi and
# refresh the vendored uniffi runtime from the SAME generator checkout.
#
# Bindings and runtime must come from one generator version: the runtime is
# the other half of the generated code's contract. They used to drift apart
# (runtime from generator 0.4.3, bindings patched to pass a newer contract
# check), which is what this script now prevents.
#
#   scripts/regenerate-uniffi.sh
#
# Env overrides:
#   KMP_BINDGEN_DIR  generator checkout (default ../uniffi-kotlin-multiplatform-bindings)
#   KMP_BINDGEN      generator binary  (default $KMP_BINDGEN_DIR/target/release/uniffi-bindgen-kotlin-multiplatform)
#   FFI_LIBRARY     already built host library (e.g. target/debug/*.dylib); skips cargo build
#   REGEN_SKIP_COMPILE=1 generate only; run a separately scoped compile before shipping
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
RUST_DIR="${ROOT_DIR}/../privchat-sdk"
GEN_DIR="${KMP_BINDGEN_DIR:-${ROOT_DIR}/../uniffi-kotlin-multiplatform-bindings}"
KMP_BINDGEN="${KMP_BINDGEN:-${GEN_DIR}/target/release/uniffi-bindgen-kotlin-multiplatform}"

if [[ ! -x "$KMP_BINDGEN" ]]; then
  echo "[regen] missing bindgen binary: $KMP_BINDGEN"
  echo "[regen] build it: (cd $GEN_DIR && cargo build --release -p uniffi_bindgen_kotlin_multiplatform)"
  exit 1
fi
echo "[regen] generator: $(git -C "$GEN_DIR" describe --tags --always 2>/dev/null || echo unknown) ($KMP_BINDGEN)"

if [[ -z "${FFI_LIBRARY:-}" ]]; then
  echo "[regen] build rust ffi dylib"
  (cd "$RUST_DIR" && cargo build -p privchat-sdk-ffi --release)
  FFI_LIBRARY="$RUST_DIR/target/release/libprivchat_sdk_ffi.dylib"
fi
test -f "$FFI_LIBRARY"

OUT_DIR="$(mktemp -d /tmp/privchat-uniffi-gen-XXXXXX)"
echo "[regen] generate bindings -> $OUT_DIR"
(cd "$RUST_DIR" && "$KMP_BINDGEN" \
  --library \
  --crate privchat_sdk_ffi \
  --config "$RUST_DIR/crates/privchat-sdk-ffi/uniffi.toml" \
  --out-dir "$OUT_DIR" \
  "$FFI_LIBRARY")

cd "$ROOT_DIR"
SRC=sdk/src
TOUCHED=(
  "$SRC/commonMain/kotlin/uniffi"
  "$SRC/androidMain/kotlin/uniffi"
  "$SRC/nativeMain/kotlin/uniffi"
  "$SRC/nativeInterop/cinterop/headers"
)
BACKUP="$(mktemp /tmp/privchat-uniffi-backup-XXXXXX).tar"
tar -cf "$BACKUP" "${TOUCHED[@]}"
restore_backup() {
  rm -rf "${TOUCHED[@]}"
  tar -xf "$BACKUP"
}

echo "[regen] install generated bindings and headers"
for ss in commonMain androidMain nativeMain; do
  install -m 0644 "$OUT_DIR/$ss/kotlin/uniffi/privchat_sdk_ffi/"*.kt "$SRC/$ss/kotlin/uniffi/privchat_sdk_ffi/"
done
# Normalize only the newly added field; preserve legacy generator formatting elsewhere.
perl -0pi -e 's/(var \x60stickerId\x60: kotlin\.String\?\n[^\n]*?)[ \t]+(?=\n)/$1/g' \
  "$SRC/commonMain/kotlin/uniffi/privchat_sdk_ffi/privchat_sdk_ffi.common.kt"
install -m 0644 "$OUT_DIR/nativeInterop/cinterop/headers/common/common.h" \
  "$SRC/nativeInterop/cinterop/headers/common/common.h"
install -m 0644 "$OUT_DIR/nativeInterop/cinterop/headers/privchat_sdk_ffi/privchat_sdk_ffi.h" \
  "$SRC/nativeInterop/cinterop/headers/privchat_sdk_ffi/privchat_sdk_ffi.h"

echo "[regen] refresh vendored runtime from the generator (keeping UniffiRuntimeBridge*)"
for ss in commonMain androidMain nativeMain; do
  dir="$SRC/$ss/kotlin/uniffi/runtime"
  find "$dir" -type f ! -name 'UniffiRuntimeBridge*.kt' -delete
  install -m 0644 "$GEN_DIR/runtime/src/$ss/kotlin/uniffi/runtime/"*.kt "$dir/"
done

echo "[regen] verify kotlin compile"
if [[ "${REGEN_SKIP_COMPILE:-0}" == 1 ]]; then
  echo "[regen] compile deferred; native mobile libraries MUST be rebuilt before device execution"
  exit 0
fi
if ! ./gradlew :sdk:compileDebugKotlinAndroid :sdk:compileKotlinIosSimulatorArm64 --no-daemon; then
  echo "[regen] generated bindings do not compile, rolling back"
  restore_backup
  exit 1
fi
echo "[regen] done"
