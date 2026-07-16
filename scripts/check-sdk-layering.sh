#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "$0")/.." && pwd)"
sdk_sources="$root/sdk/src"
app_root="$root/../privchat-app"

fail() {
  printf 'SDK layering violation: %s\n' "$1" >&2
  exit 1
}

if [[ -d "$app_root" ]] && rg -n 'import uniffi\.privchat_sdk_ffi' "$app_root" --glob '*.kt'; then
  fail 'application code imports generated UniFFI bindings directly'
fi

if rg -n 'buildJsonObject\(|rpcCall\(|"""\{' \
  "$sdk_sources/androidMain/kotlin/com/netonstream/privchat/sdk" \
  "$sdk_sources/nativeMain/kotlin/com/netonstream/privchat/sdk" \
  --glob '*.kt'; then
  fail 'platform SDK wrappers construct JSON or call generic RPC directly'
fi

for source in \
  "$sdk_sources/androidMain/kotlin/com/netonstream/privchat/sdk/PrivchatClient.android.kt" \
  "$sdk_sources/nativeMain/kotlin/com/netonstream/privchat/sdk/PrivchatClient.native.kt"; do
  rg -q 'prepareAndEnqueueAttachment\(' "$source" || fail "$source bypasses the shared attachment lifecycle"
  rg -q 'prepareAndEnqueueAttachmentBytes\(' "$source" || fail "$source bypasses the shared byte-attachment lifecycle"
done

printf 'SDK layering checks passed.\n'
