package com.ruuniv.app.keys.service

import com.ruuniv.app.keys.implement.ApiKeyWriter
import com.ruuniv.common.util.UUIDGenerator
import kotlinx.coroutines.coroutineScope
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApiKeyCommandService(
    private val apiKeyWriter: ApiKeyWriter,
) {

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheManager = "caffeineCacheManager", value = ["API_KEY_CACHE"], key = "#userId"),
            CacheEvict(cacheManager = "redisCacheManager", value = ["API_KEY_CACHE"], key = "#userId"),
        ]
    )
    suspend fun createApiKey(apiKey: String = UUIDGenerator.generateUUID(), userId: Long) = coroutineScope {
        apiKeyWriter.add(apiKey, userId)
    }

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheManager = "caffeineCacheManager", value = ["API_KEY_CACHE"], key = "#userId"),
            CacheEvict(cacheManager = "redisCacheManager", value = ["API_KEY_CACHE"], key = "#userId"),
        ]
    )
    suspend fun deleteApiKey(apiKeyId: Long, userId: Long) = coroutineScope {
        apiKeyWriter.delete(apiKeyId, userId)
    }
}