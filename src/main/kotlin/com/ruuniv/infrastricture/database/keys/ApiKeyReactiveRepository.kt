package com.ruuniv.infrastricture.database.keys

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface ApiKeyReactiveRepository : ReactiveCrudRepository<ApiKeyEntity, Long> {

    fun findByApiKey(apiKey: String): Mono<ApiKeyEntity>
}