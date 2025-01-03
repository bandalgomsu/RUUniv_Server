package com.ruuniv.infrastricture.database.users.entities

import com.ruuniv.infrastricture.database.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
class UserEntity(
    @Id
    var id: Long? = null,
    var email: String,
    var password: String,
    var role: Role
) : BaseEntity() {}