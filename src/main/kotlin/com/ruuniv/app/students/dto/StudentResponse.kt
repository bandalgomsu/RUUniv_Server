package com.ruuniv.app.students.dto

class StudentResponse {

    data class StudentVerifyResponse(
        val isStudent: Boolean,
        val universityName: String? = null,
    )
}