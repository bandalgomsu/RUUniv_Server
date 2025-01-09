package com.ruuniv.common.redis

import kotlin.reflect.KClass

interface RedisClient {
    suspend fun setData(key: String, data: Any): Boolean

    suspend fun setData(key: String, data: Any, durationSeconds: Long): Boolean

    suspend fun <T : Any> getData(key: String, type: KClass<T>): T?

    suspend fun deleteData(key: String): Boolean
}