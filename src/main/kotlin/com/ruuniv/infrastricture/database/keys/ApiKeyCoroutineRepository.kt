package com.ruuniv.infrastricture.database.keys

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ApiKeyCoroutineRepository : CoroutineCrudRepository<ApiKeyEntity, Long> {
    suspend fun findByApiKey(apiKey: String): ApiKeyEntity?
    suspend fun findAllByUserId(userId: Long): Flow<ApiKeyEntity>
}