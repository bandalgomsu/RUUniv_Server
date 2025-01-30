package com.ruuniv.infrastricture.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.ruuniv.common.redis.RedisChannelMessage
import com.ruuniv.common.redis.RedisChannelTopic
import com.ruuniv.common.redis.RedisPublisher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component

@Component
class ReactiveRedisPublisher(
    @Qualifier("reactiveRedisStringTemplate") private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) :
    RedisPublisher {

    override fun publish(channel: RedisChannelTopic, message: RedisChannelMessage) {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            reactiveRedisTemplate.convertAndSend(channel.name, objectMapper.writeValueAsString(message))
                .awaitFirstOrNull()
        }
    }
}