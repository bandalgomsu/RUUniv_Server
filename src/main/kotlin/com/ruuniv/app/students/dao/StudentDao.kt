package com.ruuniv.app.students.dao

import com.ruuniv.app.students.model.Student

interface StudentDao {
    suspend fun readAllByApiKeyId(apiKeyId: Long): List<Student>
    suspend fun add(student: Student)

}