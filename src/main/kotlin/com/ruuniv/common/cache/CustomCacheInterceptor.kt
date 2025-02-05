package com.ruuniv.common.cache

import com.ruuniv.common.redis.RedisChannelMessage
import com.ruuniv.common.redis.RedisChannelTopic
import com.ruuniv.common.redis.RedisPublisher
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.data.redis.cache.RedisCache

class CustomCacheInterceptor(
    private val caffeineCacheManager: CaffeineCacheManager,
    private val redisPublisher: RedisPublisher,
    private val redisCacheEnableState: RedisCacheEnableState
) : CacheInterceptor() {

    private var log = LoggerFactory.getLogger(CustomCacheInterceptor::class.java)

    override fun doGet(cache: Cache, key: Any): Cache.ValueWrapper? {
        if (!redisCacheEnableState.isRedisCacheEnable) {
            return null
        }

        val existingCacheValue = super.doGet(cache, key.toString())

        if (existingCacheValue != null && cache is RedisCache) {
            redisPublisher.publish(
                RedisChannelTopic.CACHE_SYNC_CHANNEL, RedisChannelMessage.CacheSyncMessage(
                    cacheName = cache.name,
                    key = key,
                    cacheValue = existingCacheValue.get()!!,
                    cacheValueClass = existingCacheValue.get()!!.javaClass
                )
            )
        }

        return existingCacheValue
    }

    private fun syncLocalCache(cache: Cache, key: Any, existingCacheValue: Cache.ValueWrapper) {
        val caffeineCache: Cache = caffeineCacheManager.getCache(cache.name) ?: return

        caffeineCache.put(key.toString(), existingCacheValue.get())
    }
}