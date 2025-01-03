package com.ruuniv.infrastricture.redis

interface RedisKey {
    fun combine(target: String): String
}