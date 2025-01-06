package com.ruuniv.infrastricture.database.keys

import com.ruuniv.app.keys.model.ApiKey
import com.ruuniv.app.keys.repository.ApiKeyRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ApiKeyEntityRepository(
    private val repository: ApiKeyCoroutineRepository,
    private val reactiveRepository: ApiKeyReactiveRepository,
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

    override fun readMono(apiKey: String): Mono<ApiKey> {
        return reactiveRepository.findByApiKey(apiKey).flatMap {
            Mono.justOrEmpty(
                ApiKey(
                    id = it.id,
                    userId = it.userId,
                    apiKey = it.apiKey,
                )
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