package com.ruuniv.app.users.model

import com.ruuniv.infrastricture.database.users.entities.Role

class User(
    val id: Long? = null,
    val email: String,
    val password: String,
    val role: Role
) {
}