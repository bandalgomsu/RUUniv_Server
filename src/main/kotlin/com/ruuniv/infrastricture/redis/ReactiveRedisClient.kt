package com.ruuniv.infrastricture.redis

import com.ruuniv.common.redis.RedisClient
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import kotlin.reflect.KClass

@Component
class ReactiveRedisClient(
    private val redisTemplate: ReactiveRedisTemplate<String, Any>
) : RedisClient {
    override suspend fun setData(key: String, data: Any): Boolean = coroutineScope {
        redisTemplate.opsForValue()
            .set(key, data)
            .awaitSingle()
    }

    override suspend fun setData(key: String, data: Any, durationSeconds: Long): Boolean = coroutineScope {
        redisTemplate.opsForValue()
            .set(key, data, Duration.ofSeconds(durationSeconds))
            .awaitSingle()
    }

    override suspend fun <T : Any> getData(key: String, type: KClass<T>): T? = coroutineScope {
        redisTemplate.opsForValue()
            .get(key)
            .awaitSingleOrNull() as T?
    }
    
    override suspend fun deleteData(key: String): Boolean = coroutineScope {
        redisTemplate.opsForValue()
            .delete(key)
            .awaitSingle()
    }
}