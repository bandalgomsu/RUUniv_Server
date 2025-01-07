package com.ruuniv.app.keys.implement

import com.ruuniv.app.keys.dao.ApiKeyDao
import com.ruuniv.app.keys.model.ApiKey
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class ApiKeyWriter(
    private val apiKeyDao: ApiKeyDao
) {

    suspend fun add(apiKey: String, userId: Long) = coroutineScope {
        apiKeyDao.add(
            ApiKey(
                apiKey = apiKey,
                userId = userId,
            )
        )
    }

    suspend fun delete(apiKeyId: Long, userId: Long) = coroutineScope {
        apiKeyDao.delete(apiKeyId, userId)
    }
}