package com.ruuniv.infrastricture.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.ruuniv.common.redis.RedisChannelMessage
import com.ruuniv.common.redis.RedisChannelTopic
import kotlinx.coroutines.reactive.collect
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer
import org.springframework.stereotype.Component

@Component
class RedisSubscriber(
    private val connectionFactory: ReactiveRedisConnectionFactory,
    private val objectMapper: ObjectMapper,
) {

    suspend fun subscribe() {
        val listenerContainer = ReactiveRedisMessageListenerContainer(connectionFactory)

        val channels = RedisChannelTopic.entries.map {
            ChannelTopic(it.name)
        }

        listenerContainer.receive(*channels.toTypedArray())
            .collect {
                objectMapper.readValue(it.message, RedisChannelMessage::class.java)
            }
    }

}