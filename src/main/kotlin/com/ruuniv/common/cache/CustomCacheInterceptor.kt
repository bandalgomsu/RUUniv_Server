package com.ruuniv.common.cache

import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.data.redis.cache.RedisCache

class CustomCacheInterceptor(
    private val caffeineCacheManager: CaffeineCacheManager,
) : CacheInterceptor() {

    private var log = LoggerFactory.getLogger(com.ruuniv.common.cache.CustomCacheInterceptor::class.java)

    override fun doGet(cache: Cache, key: Any): Cache.ValueWrapper? {
        val existingCacheValue = super.doGet(cache, key)

        log.info("CACHE : {}", cache.javaClass)
        log.info("CACHE DATA : {}", existingCacheValue)

        if (existingCacheValue != null && cache is RedisCache) {
            syncLocalCache(cache, key, existingCacheValue)
        }

        return existingCacheValue
    }

    private fun syncLocalCache(cache: Cache, key: Any, existingCacheValue: Cache.ValueWrapper) {
        val caffeineCache: Cache = caffeineCacheManager.getCache(cache.name) ?: return

        caffeineCache.put(key, existingCacheValue.get())
    }
}