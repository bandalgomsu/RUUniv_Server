package com.ruuniv.app.keys.service

import com.ruuniv.app.keys.implement.ApiKeyWriter
import com.ruuniv.app.keys.model.ApiKey
import com.ruuniv.common.util.UUIDGenerator
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApiKeyCommandService(
    private val apiKeyWriter: ApiKeyWriter,
) {

    @Transactional
    suspend fun createApiKey(userId: Long, apiKeyValue: String = UUIDGenerator.generateUUID()) = coroutineScope {
        val apiKey = ApiKey(
            apiKey = apiKeyValue,
            userId = userId
        )

        apiKeyWriter.add(apiKey)
    }

    @Transactional
    suspend fun deleteApiKey(apiKeyId: Long, userId: Long) = coroutineScope {
        apiKeyWriter.delete(apiKeyId, userId)
    }
}