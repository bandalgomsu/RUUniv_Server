package com.ruuniv.infrastricture.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.ruuniv.common.redis.RedisChannelMessage
import com.ruuniv.common.redis.RedisChannelTopic
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.collect
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer
import org.springframework.stereotype.Component

@Component
class RedisCacheSyncSubscriber(
    private val connectionFactory: ReactiveRedisConnectionFactory,
    private val objectMapper: ObjectMapper,
    private val caffeineCacheManager: CaffeineCacheManager,
) {
    private val log = LoggerFactory.getLogger(RedisCacheSyncSubscriber::class.java)

    @PostConstruct
    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            subscribe()
        }
    }

    suspend fun subscribe() {
        val listenerContainer = ReactiveRedisMessageListenerContainer(connectionFactory)

        val channels = RedisChannelTopic.entries.map {
            ChannelTopic(it.name)
        }

        listenerContainer.receive(*channels.toTypedArray())
            .collect {
                if (it.channel == RedisChannelTopic.CACHE_SYNC_CHANNEL.name) {
                    val message = objectMapper.readValue(it.message, RedisChannelMessage.CacheSyncMessage::class.java)

                    syncLocalCache(message.cacheName, message.key, message.cacheValueClass, message.cacheValue)
                }
            }
    }

    private suspend fun syncLocalCache(
        cacheName: String,
        key: Any,
        cacheValueClass: Class<*>,
        existingCacheValue: Any
    ) {
        val caffeineCache: Cache = caffeineCacheManager.getCache(cacheName) ?: return

        caffeineCache.put(key.toString(), objectMapper.convertValue(existingCacheValue, cacheValueClass))
        log.info("SYNC : {}", caffeineCache.get(key.toString()))
        log.info("CLASS {}", existingCacheValue.javaClass)
    }
}