package com.ruuniv.infrastricture.dao.repository.endpoint

import com.ruuniv.infrastricture.dao.repository.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("end_point")
class EndPointEntity(
    @Id
    var id: Long? = null,
    @Column("end_point")
    var endPoint: String //Unique
) : BaseEntity() {
}