package com.ruuniv.common.redis

interface RedisKey {
    fun combine(target: String): String
}