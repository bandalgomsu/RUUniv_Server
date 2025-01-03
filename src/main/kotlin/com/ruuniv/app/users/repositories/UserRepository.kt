package com.ruuniv.app.users.repositories

import com.ruuniv.app.users.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    suspend fun read(userId: Long): User?
}