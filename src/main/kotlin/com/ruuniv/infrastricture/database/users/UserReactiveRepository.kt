package com.ruuniv.infrastricture.database.users

import com.ruuniv.infrastricture.database.users.entities.UserEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserReactiveRepository : ReactiveCrudRepository<UserEntity, Long> {
    fun findByEmail(email: String): Mono<UserEntity>
}