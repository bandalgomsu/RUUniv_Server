package com.ruuniv.app.students.repository

import com.ruuniv.app.students.model.Student

interface StudentRepository {
    suspend fun readAllByApiKeyId(apiKeyId: Long): List<Student>
    suspend fun add(student: Student)

}