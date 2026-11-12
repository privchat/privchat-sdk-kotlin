#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
RUST_DIR="$ROOT_DIR/privchat-sdk"
KOTLIN_DIR="$ROOT_DIR/privchat-sdk-kotlin"

if [[ -f "$HOME/.zshrc" ]]; then
  # shellcheck disable=SC1090
  set +u
  source "$HOME/.zshrc"
  set -u
fi

if [[ -z "${JAVA_HOME:-}" ]]; then
  JAVA_HOME="$("/usr/libexec/java_home" -v 17)"
  export JAVA_HOME
  export PATH="$JAVA_HOME/bin:$PATH"
fi

echo "[gate] sdk layering (fast, no build)"
cd "$KOTLIN_DIR"
./scripts/check-sdk-layering.sh

echo "[gate] cargo check"
cd "$RUST_DIR"
cargo check -p privchat-sdk -p privchat-sdk-ffi

echo "[gate] cargo test (sdk core)"
cargo test -p privchat-sdk --lib

echo "[gate] kotlin compile (ios+android)"
cd "$KOTLIN_DIR"
./gradlew :sdk:compileKotlinIosSimulatorArm64 :sdk:compileDebugKotlinAndroid --no-daemon

echo "[gate] done"
