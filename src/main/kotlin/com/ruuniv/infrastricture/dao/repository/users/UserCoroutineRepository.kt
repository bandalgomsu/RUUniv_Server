package com.ruuniv.infrastricture.dao.repository.users

import com.ruuniv.infrastricture.dao.repository.users.entities.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserCoroutineRepository : CoroutineCrudRepository<UserEntity, Long> {

    suspend fun findByEmail(email: String): UserEntity?
}