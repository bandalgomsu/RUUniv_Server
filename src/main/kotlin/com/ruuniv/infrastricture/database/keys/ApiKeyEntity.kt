package com.ruuniv.infrastricture.database.keys

import com.ruuniv.infrastricture.database.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("api_key")
class ApiKeyEntity(
    @Id
    var id: Long? = null,
    @Column("api_key")
    var apiKey: String,
    @Column("user_id")
    var userId: Long,
) : BaseEntity() {}