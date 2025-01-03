package com.ruuniv.infrastricture.database.users

import com.ruuniv.infrastricture.database.users.entities.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserCoroutineRepository : CoroutineCrudRepository<User, Long> {

    suspend fun findByEmail(email: String): User?
}