package com.ruuniv.infrastricture.dao.repository.users

import com.ruuniv.app.users.dao.UserDao
import com.ruuniv.app.users.model.User
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Repository

@Repository
class UserEntityDao(
    private val repository: UserCoroutineRepository
) : UserDao {
    override suspend fun read(userId: Long): User? = coroutineScope {
        repository.findById(userId)?.let {
            User(
                id = it.id,
                email = it.email,
                password = it.password,
                role = it.role,
            )
        }
    }

    override suspend fun read(email: String): User? = coroutineScope {
        repository.findByEmail(email)?.let {
            User(
                id = it.id,
                email = it.email,
                password = it.password,
                role = it.role,
            )
        }
    }
}