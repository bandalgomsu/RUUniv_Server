package com.ruuniv.infrastricture.database.users

import com.ruuniv.infrastricture.database.users.entities.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserCoroutineRepository : CoroutineCrudRepository<UserEntity, Long> {

    suspend fun findByEmail(email: String): UserEntity?
}