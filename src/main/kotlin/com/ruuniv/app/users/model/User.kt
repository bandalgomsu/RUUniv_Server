package com.ruuniv.app.users.model

import com.ruuniv.infrastricture.dao.repository.users.entities.Role

class User(
    val id: Long? = null,
    val email: String,
    val password: String,
    val role: Role
) {
}