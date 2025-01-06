package com.ruuniv.app.keys.repository

import com.ruuniv.app.keys.model.ApiKey
import reactor.core.publisher.Mono

interface ApiKeyRepository {
    suspend fun add(apiKey: ApiKey)
    suspend fun read(apiKeyId: Long): ApiKey?
    suspend fun read(apiKey: String): ApiKey?

    suspend fun readAll(userId: Long): List<ApiKey>

    fun readMono(apiKey: String): Mono<ApiKey>
}