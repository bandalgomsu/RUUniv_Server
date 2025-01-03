package com.ruuniv.infrastricture.database.keys

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ApiKeyCoroutineRepository : CoroutineCrudRepository<ApiKeyEntity, Long> {
    suspend fun findByApiKey(apiKey: String): ApiKeyEntity?
}