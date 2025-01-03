package com.ruuniv.infrastricture.database.users

import com.ruuniv.app.users.model.User
import com.ruuniv.app.users.repositories.UserRepository
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Repository

@Repository
class UserEntityRepository(
    private val repository: UserCoroutineRepository
) : UserRepository {
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
}