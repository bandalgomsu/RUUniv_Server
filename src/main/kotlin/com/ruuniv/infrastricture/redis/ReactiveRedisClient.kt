package com.ruuniv.infrastricture.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.ruuniv.common.redis.RedisClient
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import kotlin.reflect.KClass

@Component
class ReactiveRedisClient(
    @Qualifier("reactiveRedisStringTemplate") private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : RedisClient {
    override suspend fun <T> setData(key: String, data: T): Boolean = coroutineScope {
        redisTemplate.opsForValue()
            .set(key, objectMapper.writeValueAsString(data))
            .awaitSingle()
    }

    override suspend fun <T> setData(key: String, data: T, durationSeconds: Long): Boolean = coroutineScope {
        redisTemplate.opsForValue()
            .set(key, objectMapper.writeValueAsString(data), Duration.ofSeconds(durationSeconds))
            .awaitSingle()
    }

    override suspend fun <T : Any> getData(key: String, type: KClass<T>): T? = coroutineScope {
        redisTemplate.opsForValue()
            .get(key)
            .awaitSingleOrNull()
            ?.let {
                objectMapper.readValue(it, type.java)
            }
    }

    override suspend fun deleteData(key: String): Boolean = coroutineScope {
        redisTemplate.opsForValue()
            .delete(key)
            .awaitSingle()
    }
}