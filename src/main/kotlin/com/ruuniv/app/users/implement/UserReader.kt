package com.ruuniv.app.users.implement

import com.ruuniv.app.users.repositories.UserRepository
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {
}