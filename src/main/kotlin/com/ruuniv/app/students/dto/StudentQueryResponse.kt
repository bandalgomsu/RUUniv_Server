package com.ruuniv.app.students.dto

import com.fasterxml.jackson.annotation.JsonProperty

class StudentQueryResponse {
    data class StudentsInfo(
        @JsonProperty("studentsInfo") val studentsInfo: List<StudentInfo> = emptyList()
    )

    data class StudentInfo(
        @JsonProperty("studentId") val studentId: Long,
        @JsonProperty("studentEmail") val studentEmail: String,
    )
}