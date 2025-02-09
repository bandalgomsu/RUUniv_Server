package com.ruuniv.common.redis

interface RedisPublisher {

    fun publish(channel: RedisChannelTopic, message: RedisChannelMessage)
}