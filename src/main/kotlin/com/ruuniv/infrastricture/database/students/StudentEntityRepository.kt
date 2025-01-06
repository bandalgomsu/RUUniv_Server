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

    override suspend fun readAllByApiKeyId(apiKeyId: Long): List<Student> = coroutineScope {
        repository.findAllByApiKeyId(apiKeyId).map {
            Student(
                id = it.id,
                apiKeyId = it.apiKeyId,
                email = it.email,
                universityName = it.universityName
            )
        }.toList()
    }

    override suspend fun add(student: Student) {
        repository.save(
            StudentEntity(
                apiKeyId = student.apiKeyId,
                email = student.email,
                universityName = student.universityName
            )
        )
    }
}