package com.ruuniv.app.students.implement

import com.ruuniv.app.students.model.Student
import com.ruuniv.app.students.repository.StudentRepository
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class StudentWriter(
    private val repository: StudentRepository
) {

    suspend fun add(student: Student) = coroutineScope {
        repository.add(student)
    }
}