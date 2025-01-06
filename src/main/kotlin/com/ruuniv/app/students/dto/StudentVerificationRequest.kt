package com.ruuniv.app.students.dto

class StudentVerificationRequest {

    data class SendStudentVerificationMailRequest(
        val email: String,
    )

    data class VerifyStudentVerificationAuthNumberRequest(
        val email: String,
        val authNumber: String,
    )
}