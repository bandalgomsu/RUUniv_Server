package com.ruuniv.infrastricture.database.students

import com.ruuniv.app.students.model.Student
import com.ruuniv.app.students.repository.StudentRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository

@Repository
class StudentEntityRepository(
    private val repository: StudentCoroutineRepository
) : StudentRepository {

    override suspend fun read(userId: Long, apiKeyId: Long): Student? = coroutineScope {
        repository.findByUserIdAndApiKeyId(userId, apiKeyId)?.let {
            Student(
                id = it.id,
                userId = it.userId,
                apiKeyId = it.apiKeyId,
                email = it.email,
                universityName = it.universityName
            )
        }
    }

    override suspend fun readAllByUserId(userId: Long): List<Student> = coroutineScope {
        repository.findAllByUserId(userId).map {
            Student(
                id = it.id,
                userId = it.userId,
                apiKeyId = it.apiKeyId,
                email = it.email,
                universityName = it.universityName
            )
        }.toList()
    }

    override suspend fun readAllByApiKeyId(apiKeyId: Long): List<Student> = coroutineScope {
        repository.findAllByApiKeyId(apiKeyId).map {
            Student(
                id = it.id,
                userId = it.userId,
                apiKeyId = it.apiKeyId,
                email = it.email,
                universityName = it.universityName
            )
        }.toList()
    }

    override suspend fun add(student: Student) {
        repository.save(
            StudentEntity(
                userId = student.userId,
                apiKeyId = student.apiKeyId,
                email = student.email,
                universityName = student.universityName
            )
        )
    }
}