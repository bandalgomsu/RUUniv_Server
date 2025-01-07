package com.ruuniv.app.keys.implement

import com.ruuniv.app.keys.model.ApiKey
import com.ruuniv.app.keys.repository.ApiKeyRepository
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class ApiKeyWriter(
    private val apiKeyRepository: ApiKeyRepository
) {

    suspend fun add(apiKey: String, userId: Long) = coroutineScope {
        apiKeyRepository.add(
            ApiKey(
                apiKey = apiKey,
                userId = userId,
            )
        )
    }

    suspend fun delete(apiKeyId: Long, userId: Long) = coroutineScope {
        apiKeyRepository.delete(apiKeyId, userId)
    }
}