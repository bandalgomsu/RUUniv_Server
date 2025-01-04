package com.ruuniv.infrastricture.database.keys

import com.ruuniv.app.keys.model.ApiKey
import com.ruuniv.app.keys.repository.ApiKeyRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository

@Repository
class ApiKeyEntityRepository(
    private val repository: ApiKeyCoroutineRepository
) : ApiKeyRepository {

    override suspend fun add(apiKey: ApiKey): Unit = coroutineScope {
        repository.save(
            ApiKeyEntity(
                apiKey = apiKey.apiKey,
                userId = apiKey.userId,
            )
        )
    }

    override suspend fun read(apiKeyId: Long): ApiKey? = coroutineScope {
        repository.findById(apiKeyId)?.let {
            ApiKey(
                id = it.id,
                apiKey = it.apiKey,
                userId = it.userId,
            )
        }
    }

    override suspend fun read(apiKey: String): ApiKey? = coroutineScope {
        repository.findByApiKey(apiKey)?.let {
            ApiKey(
                id = it.id,
                apiKey = it.apiKey,
                userId = it.userId,
            )
        }
    }

    override suspend fun readAll(userId: Long): List<ApiKey> = coroutineScope {
        repository.findAllByUserId(userId).map {
            ApiKey(
                id = it.id,
                apiKey = it.apiKey,
                userId = it.userId
            )
        }.toList()
    }
}