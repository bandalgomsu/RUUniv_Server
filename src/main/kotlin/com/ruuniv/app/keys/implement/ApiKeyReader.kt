package com.ruuniv.app.keys.implement

import com.ruuniv.app.keys.exception.ApiKeyErrorCode
import com.ruuniv.app.keys.model.ApiKey
import com.ruuniv.app.keys.repository.ApiKeyRepository
import com.ruuniv.common.exception.BusinessException
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class ApiKeyReader(
    private var apiKeyRepository: ApiKeyRepository,
) {
    suspend fun read(apiKey: String): ApiKey = coroutineScope {
        apiKeyRepository.read(apiKey) ?: throw BusinessException(ApiKeyErrorCode.NOT_EXISTS_API_KEY)
    }

    suspend fun readAll(userId: Long): List<ApiKey> = coroutineScope {
        apiKeyRepository.readAll(userId)
    }
}