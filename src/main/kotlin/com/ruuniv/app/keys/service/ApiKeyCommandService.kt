package com.ruuniv.app.keys.service

import com.ruuniv.app.keys.implement.ApiKeyWriter
import com.ruuniv.common.util.UUIDGenerator
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApiKeyCommandService(
    private val apiKeyWriter: ApiKeyWriter,
) {

    @Transactional
    suspend fun createApiKey(apiKey: String = UUIDGenerator.generateUUID(), userId: Long) = coroutineScope {
        apiKeyWriter.add(apiKey, userId)
    }

    @Transactional
    suspend fun deleteApiKey(apiKeyId: Long, userId: Long) = coroutineScope {
        apiKeyWriter.delete(apiKeyId, userId)
    }
}