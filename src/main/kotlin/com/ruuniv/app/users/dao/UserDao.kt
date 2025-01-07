package com.ruuniv.app.users.dao

import com.ruuniv.app.users.model.User

interface UserDao {
    suspend fun read(userId: Long): User?
    suspend fun read(email: String): User?
}