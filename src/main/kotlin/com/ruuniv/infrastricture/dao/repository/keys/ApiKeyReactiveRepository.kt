package com.ruuniv.infrastricture.dao.repository.keys

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface ApiKeyReactiveRepository : ReactiveCrudRepository<ApiKeyEntity, Long> {

    fun findByApiKey(apiKey: String): Mono<ApiKeyEntity>
}