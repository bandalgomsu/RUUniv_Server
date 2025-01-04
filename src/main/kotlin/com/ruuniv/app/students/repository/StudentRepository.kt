package com.ruuniv.app.students.repository

import com.ruuniv.app.students.model.Student

interface StudentRepository {

    suspend fun read(userId: Long, apiKeyId: Long): Student?

    suspend fun readAllByUserId(userId: Long): List<Student>
    suspend fun readAllByApiKeyId(apiKeyId: Long): List<Student>

    suspend fun add(student: Student)

}