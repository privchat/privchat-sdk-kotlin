package om.netonstream.privchat.sdk

object SdkErrorCodes {
    const val DOMAIN_MASK: UInt = 0xFF000000u
    const val DOMAIN_SHIFT: Int = 24

    const val DOMAIN_TRANSPORT: UInt = 0x01u
    const val DOMAIN_STORAGE: UInt = 0x02u
    const val DOMAIN_AUTH: UInt = 0x03u
    const val DOMAIN_SYNC: UInt = 0x04u
    const val DOMAIN_STATE: UInt = 0x05u
    const val DOMAIN_ACTOR: UInt = 0x06u
    const val DOMAIN_SERIALIZATION: UInt = 0x07u
    const val DOMAIN_SHUTDOWN: UInt = 0x08u
    const val DOMAIN_INTERNAL: UInt = 0x0Fu

    const val TRANSPORT_FAILURE: UInt = 0x01000001u
    const val NETWORK_DISCONNECTED: UInt = 0x01000002u
    const val AUTH_FAILURE: UInt = 0x03000001u
    const val STORAGE_FAILURE: UInt = 0x02000001u
    const val SERIALIZATION_FAILURE: UInt = 0x07000001u
    const val INVALID_STATE: UInt = 0x05000001u
    const val ACTOR_CLOSED: UInt = 0x06000001u
    const val SHUTDOWN: UInt = 0x08000001u
    const val INTERNAL_UNKNOWN: UInt = 0x0F000001u

    fun domain(code: UInt): UInt = (code and DOMAIN_MASK) shr DOMAIN_SHIFT
}

