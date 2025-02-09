package com.ruuniv.common.cache.controller

import com.ruuniv.common.cache.model.CacheType
import com.ruuniv.common.exception.BusinessException
import com.ruuniv.common.exception.CommonErrorCode
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.coroutineScope
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "캐시")
class CacheController(
    private val caffeineCacheManager: CaffeineCacheManager,
) {

    @DeleteMapping("/api/v1/cache/{cache}/{cacheKey}")
    suspend fun evict(@PathVariable cache: CacheType, @PathVariable cacheKey: String): ResponseEntity<Unit> =
        coroutineScope {
            val evictedCache = caffeineCacheManager.getCache(cache.cacheName)
                ?: throw BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR)

            evictedCache.evict(cacheKey)

            return@coroutineScope ResponseEntity.ok().build()
        }
}