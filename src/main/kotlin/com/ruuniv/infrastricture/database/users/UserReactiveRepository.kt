package com.ruuniv.infrastricture.database.users

import com.ruuniv.infrastricture.database.users.entities.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserReactiveRepository : ReactiveCrudRepository<User, Long> {
    fun findByEmail(email: String): Mono<User>
}