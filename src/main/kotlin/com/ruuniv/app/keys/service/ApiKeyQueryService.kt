package com.ruuniv.app.keys.service

import com.ruuniv.app.keys.dto.ApiKeyQueryResponse
import com.ruuniv.app.keys.implement.ApiKeyReader
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class ApiKeyQueryService(
    private val apiKeyReader: ApiKeyReader,
) {

    suspend fun getApiKeysByUserId(userId: Long): ApiKeyQueryResponse.ApiKeysInfo = coroutineScope {
        val apiKeysInfo = apiKeyReader.readAll(userId).map {
            ApiKeyQueryResponse.ApiKeyInfo(
                apiKeyId = it.id!!,
                apiKey = it.apiKey
            )
        }

        ApiKeyQueryResponse.ApiKeysInfo(apiKeysInfo)
    }
}