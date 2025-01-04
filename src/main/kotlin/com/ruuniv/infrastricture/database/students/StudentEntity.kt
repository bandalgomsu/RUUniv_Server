package com.ruuniv.infrastricture.database.students

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("student")
class StudentEntity(
    @Id
    var id: Long? = null,
    @Column("api_key_id")
    var apiKeyId: Long,
    var email: String,
    @Column("university_name")
    var universityName: String,
) {
}