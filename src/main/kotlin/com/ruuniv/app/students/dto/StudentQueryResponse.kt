package com.ruuniv.app.students.dto

import java.io.Serializable

class StudentQueryResponse {
    data class StudentsInfo(
        val studentsInfo: List<StudentInfo> = emptyList()
    ) : Serializable

    data class StudentInfo(
        val studentId: Long,
        val studentEmail: String,
    ) : Serializable
}