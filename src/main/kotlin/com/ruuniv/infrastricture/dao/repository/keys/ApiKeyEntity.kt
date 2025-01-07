package com.ruuniv.infrastricture.dao.repository.keys

import com.ruuniv.infrastricture.dao.repository.BaseEntity
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