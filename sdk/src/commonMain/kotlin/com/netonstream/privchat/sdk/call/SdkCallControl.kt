package com.netonstream.privchat.sdk.call

import com.netonstream.privchat.sdk.PrivchatClient

fun createCallControl(client: PrivchatClient): CallControl =
    RpcCallControl(CallRpcTransport { route, body -> client.callRpc(route, body) })
