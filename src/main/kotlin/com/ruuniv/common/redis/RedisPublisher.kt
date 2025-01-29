package com.ruuniv.common.redis

interface RedisPublisher {

    suspend fun publish(channel: String, message: RedisChannelMessage)
}