package com.ruuniv.app.users.implement

import com.ruuniv.app.users.dao.UserDao
import com.ruuniv.app.users.exception.UserErrorCode
import com.ruuniv.app.users.model.User
import com.ruuniv.common.exception.BusinessException
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userDao: UserDao
) {
    suspend fun read(userId: Long): User = coroutineScope {
        userDao.read(userId) ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
    }
}