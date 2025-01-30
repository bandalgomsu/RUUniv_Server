package com.ruuniv.infrastricture.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.ruuniv.common.redis.RedisChannelMessage
import com.ruuniv.common.redis.RedisChannelTopic
import com.ruuniv.common.redis.RedisPublisher
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate

class ReactiveRedisPublisher(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) :
    RedisPublisher {

    override suspend fun publish(channel: RedisChannelTopic, message: RedisChannelMessage) {
        reactiveRedisTemplate.convertAndSend(channel.name, objectMapper.writeValueAsString(message))
            .awaitFirstOrNull()
    }
}