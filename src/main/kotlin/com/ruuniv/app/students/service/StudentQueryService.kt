package com.ruuniv.app.students.service

import com.ruuniv.app.students.dto.StudentQueryResponse
import com.ruuniv.app.students.implement.StudentReader
import kotlinx.coroutines.coroutineScope
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service

@Service
class StudentQueryService(
    private val studentReader: StudentReader,
) {

    @Caching(
        cacheable = [
            Cacheable(cacheManager = "redisCacheManager", value = ["STUDENTS_BY_API_KEY"], key = "#apiKeyId"),
        ]
    )
    suspend fun getStudentsByApiKeyId(apiKeyId: Long): StudentQueryResponse.StudentsInfo = coroutineScope {
        val students = studentReader.readAll(apiKeyId).map {
            StudentQueryResponse.StudentInfo(
                studentId = it.id!!,
                studentEmail = it.email
            )
        }.toList()

        return@coroutineScope StudentQueryResponse.StudentsInfo(students)
    }
}