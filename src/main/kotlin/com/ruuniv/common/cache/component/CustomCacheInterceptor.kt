package com.ruuniv.common.cache.component

import com.ruuniv.app.endpoint.implement.EndPointReader
import com.ruuniv.app.endpoint.implement.SelfEndPointStorage
import com.ruuniv.common.cache.model.CacheType
import com.ruuniv.common.redis.RedisChannelMessage
import com.ruuniv.common.redis.RedisChannelTopic
import com.ruuniv.common.redis.RedisPublisher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.data.redis.cache.RedisCache
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

class CustomCacheInterceptor(
    private val caffeineCacheManager: CaffeineCacheManager,
    private val redisPublisher: RedisPublisher,
    private val redisCacheEnableState: RedisCacheEnableState,

    private val endPointReader: EndPointReader,
    private val selfEndPointStorage: SelfEndPointStorage,
) : CacheInterceptor() {

    private var log = LoggerFactory.getLogger(CustomCacheInterceptor::class.java)

    override fun doGet(cache: Cache, key: Any): Cache.ValueWrapper? {
        if (!redisCacheEnableState.isRedisCacheEnable && cache is RedisCache) {
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
    
    override fun doEvict(cache: Cache, key: Any, immediate: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            handleRedisCacheDisable(redisCacheEnableState.isRedisCacheEnable, CacheType.valueOf(cache.name), key)
        }

        super.doEvict(cache, key, immediate)
    }

    private suspend fun handleRedisCacheDisable(isEnable: Boolean, cache: CacheType, cacheKey: Any): Unit =
        coroutineScope {
            if (isEnable) {
                return@coroutineScope
            }

            val endPoints = endPointReader.readAll()

            endPoints.forEach {
                if (it.endPoint == selfEndPointStorage.selfEndPoint) {
                    return@forEach
                }

                CoroutineScope(Dispatchers.IO + coroutineContext).launch {
                    WebClient.builder()
                        .baseUrl("http://${it.endPoint}/cache/${cache.cacheName}/${cacheKey}")
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build()
                        .delete()
                }
            }
        }
}