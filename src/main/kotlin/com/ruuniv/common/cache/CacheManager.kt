package com.ruuniv.common.cache

enum class CacheManager(val cacheManagerName: String) {
    CAFFEINE("caffeineCacheManager"),
    REDIS("redisCacheManager"),
}