package com.ruuniv.common.redis

import java.io.Serializable

open class RedisChannelMessage : Serializable {
    data class CacheSyncMessage(
        val cacheName: String,
        val key: Any,
        val cacheValue: Any,
        val cacheValueClass: Class<*>
    ) : RedisChannelMessage(), Serializable {

    }
}