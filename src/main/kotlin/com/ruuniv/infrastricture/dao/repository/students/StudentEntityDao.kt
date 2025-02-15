package com.ruuniv.infrastricture.dao.repository.students

import com.ruuniv.app.students.dao.StudentDao
import com.ruuniv.app.students.model.Student
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository

@Repository
class StudentEntityDao(
    private val repository: StudentCoroutineRepository
) : StudentDao {

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