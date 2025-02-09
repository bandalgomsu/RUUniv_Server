package com.ruuniv.common.cache.model

enum class CacheType(
    val cacheName: String,
    val expireAfterWrite: Long,
    val maxSize: Long
) {
    API_KEY("API_KEY", 3600, 10000),
    STUDENT("STUDENT", 3600, 10000),
}