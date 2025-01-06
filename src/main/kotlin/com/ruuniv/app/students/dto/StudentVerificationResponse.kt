package com.ruuniv.app.students.dto

class StudentVerificationResponse {

    data class StudentVerifyResponse(
        val isStudent: Boolean,
        val universityName: String? = null,
    )
}