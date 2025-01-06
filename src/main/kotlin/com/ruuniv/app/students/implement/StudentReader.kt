package com.ruuniv.app.students.implement

import com.ruuniv.app.students.exception.StudentErrorCode
import com.ruuniv.app.students.model.Student
import com.ruuniv.app.students.repository.StudentRepository
import com.ruuniv.common.exception.BusinessException
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class StudentReader(
    private val repository: StudentRepository
) {
    
}