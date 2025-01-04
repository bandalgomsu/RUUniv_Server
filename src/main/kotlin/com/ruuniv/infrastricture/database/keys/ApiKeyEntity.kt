package com.ruuniv.infrastricture.database.keys

import com.ruuniv.infrastricture.database.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("api_key")
class ApiKeyEntity(
    @Id
    var id: Long? = null,
    var apiKey: String,
    var userId: Long,
) : BaseEntity() {}