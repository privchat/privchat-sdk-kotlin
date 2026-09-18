package com.netonstream.privchat.sdk

/**
 * 频道类型的 **wire 编号**——与 privchat-protocol `protocol::ChannelType` 一致,所有端唯一真源:
 * 1=Direct, 2=Group, 3=Room。**没有 0**(0 是"未填",服务端拒绝)。
 * 服务端数据库内部另有 0/1/2 的存储编号,绝不出现在线上。
 *
 * `PrivchatClient` 的 `channelType: Int / UByte` 参数一律传 [wire];用 [fromWire] 解析收到的值。
 */
enum class ChannelType(val wire: Int) {
    DIRECT(1),
    GROUP(2),
    ROOM(3);

    val wireUByte: UByte get() = wire.toUByte()

    val isRoom: Boolean get() = this == ROOM

    companion object {
        fun fromWire(value: Int): ChannelType? = entries.firstOrNull { it.wire == value }
        fun fromWire(value: UByte): ChannelType? = fromWire(value.toInt())
    }
}

/** 类型化的订阅入口:`expect class` 不能带默认实现,所以放在扩展函数里。 */
suspend fun PrivchatClient.subscribeChannel(channelId: ULong, channelType: ChannelType, token: String? = null): Result<Unit> =
    subscribeChannel(channelId, channelType.wireUByte, token)

suspend fun PrivchatClient.unsubscribeChannel(channelId: ULong, channelType: ChannelType): Result<Unit> =
    unsubscribeChannel(channelId, channelType.wireUByte)
