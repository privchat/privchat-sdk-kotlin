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

# 平台 wrapper 只能用强类型 FFI record 构造跨层契约（SDK_LAYERED_REARCHITECTURE_SPEC §11.1）。
# 禁止 buildJsonObject（含尾 lambda `buildJsonObject {` 与 import）、裸字符串 JSON、
# 以及硬编码业务路由的 generic rpcCall。唯一例外是 callRpc 传输 seam：它转发 commonMain
# （CallRpcTransport / ComponentConfig）已构造好的契约，用变量 route/body，不在 wrapper
# 里构造任何契约——所以放行 `rpcCall(route, body)`，其余 rpcCall 形态一律拒绝。
wrapper_dirs=(
  "$sdk_sources/androidMain/kotlin/com/netonstream/privchat/sdk"
  "$sdk_sources/nativeMain/kotlin/com/netonstream/privchat/sdk"
)

if rg -n 'buildJsonObject|rpcCall\("|"""\{' "${wrapper_dirs[@]}" --glob '*.kt'; then
  fail 'platform SDK wrappers construct JSON or hardcode business RPC routes (use typed FFI records)'
fi

if rg -n 'rpcCall\(' "${wrapper_dirs[@]}" --glob '*.kt' | rg -v 'rpcCall\(route, body\)'; then
  fail 'platform SDK wrappers call generic RPC outside the callRpc transport seam'
fi

for source in \
  "$sdk_sources/androidMain/kotlin/com/netonstream/privchat/sdk/PrivchatClient.android.kt" \
  "$sdk_sources/nativeMain/kotlin/com/netonstream/privchat/sdk/PrivchatClient.native.kt"; do
  rg -q 'prepareAndEnqueueAttachment\(' "$source" || fail "$source bypasses the shared attachment lifecycle"
  rg -q 'prepareAndEnqueueAttachmentBytes\(' "$source" || fail "$source bypasses the shared byte-attachment lifecycle"
done

printf 'SDK layering checks passed.\n'
