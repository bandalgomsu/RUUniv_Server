package com.ruuniv.app.keys.implement

import com.ruuniv.app.keys.dao.ApiKeyDao
import com.ruuniv.app.keys.exception.ApiKeyErrorCode
import com.ruuniv.app.keys.model.ApiKey
import com.ruuniv.common.exception.BusinessException
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class ApiKeyReader(
    private var apiKeyDao: ApiKeyDao,
) {
    suspend fun read(apiKey: String): ApiKey = coroutineScope {
        apiKeyDao.read(apiKey) ?: throw BusinessException(ApiKeyErrorCode.NOT_EXISTS_API_KEY)
    }

    suspend fun readAll(userId: Long): List<ApiKey> = coroutineScope {
        apiKeyDao.readAll(userId)
    }
}