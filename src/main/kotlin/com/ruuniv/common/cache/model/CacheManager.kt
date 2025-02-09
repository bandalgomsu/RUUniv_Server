package com.ruuniv.common.cache.model

enum class CacheManager(val cacheManagerName: String) {
    CAFFEINE("caffeineCacheManager"),
    REDIS("redisCacheManager"),
}