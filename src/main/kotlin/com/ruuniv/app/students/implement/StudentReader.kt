package com.ruuniv.app.students.implement

import com.ruuniv.app.students.dao.StudentDao
import com.ruuniv.app.students.model.Student
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class StudentReader(
    private val studentDao: StudentDao
) {

    suspend fun readAll(apiKeyId: Long): List<Student> = coroutineScope {
        studentDao.readAllByApiKeyId(apiKeyId)
    }
}