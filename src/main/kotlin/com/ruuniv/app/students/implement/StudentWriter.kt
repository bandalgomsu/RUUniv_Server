package com.ruuniv.app.students.implement

import com.ruuniv.app.students.dao.StudentDao
import com.ruuniv.app.students.model.Student
import com.ruuniv.app.students.model.University
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StudentWriter(
    private val repository: StudentDao
) {

    @Transactional
    suspend fun add(apiKeyId: Long, email: String, university: University) = coroutineScope {
        repository.add(
            Student(
                apiKeyId = apiKeyId,
                email = email,
                universityName = university.universityName
            )
        )
    }
}