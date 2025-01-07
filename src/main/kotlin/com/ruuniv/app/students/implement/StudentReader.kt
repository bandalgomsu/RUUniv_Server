package com.ruuniv.app.students.implement

import com.ruuniv.app.students.dao.StudentDao
import org.springframework.stereotype.Component

@Component
class StudentReader(
    private val repository: StudentDao
) {

}