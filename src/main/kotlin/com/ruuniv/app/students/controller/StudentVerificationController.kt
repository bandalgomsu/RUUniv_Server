package com.ruuniv.app.students.controller

import com.ruuniv.app.students.dto.StudentVerificationRequest
import com.ruuniv.app.students.dto.StudentVerificationResponse
import com.ruuniv.app.students.service.StudentVerificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.coroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "학생 인증", description = "학생 인증을 관리합니다")
@RestController
@RequestMapping("/api/v1/student/verification")
class StudentVerificationController(
    private val studentVerificationService: StudentVerificationService,
) {

    @PostMapping
    @Operation(summary = "학생 인증 메일 발송", description = "학생 인증 메일을 발송 합니다")
    suspend fun sendStudentVerificationMail(
        @RequestBody request: StudentVerificationRequest.SendStudentVerificationMailRequest
    ): ResponseEntity<Void> = coroutineScope {
        studentVerificationService.sendStudentVerificationMail(request.email)

        return@coroutineScope ResponseEntity.ok().build()
    }

    @PostMapping("/verify")
    @Operation(summary = "학생 인증 번호 검증", description = "학생 인증 메일을 검증 합니다")
    suspend fun verifyStudentVerificationAuthNumber(
        @RequestBody request: StudentVerificationRequest.VerifyStudentVerificationAuthNumberRequest,
        @RequestBody apiKeyId: Long
    ): ResponseEntity<StudentVerificationResponse.StudentVerifyResponse> = coroutineScope {
        val response =
            studentVerificationService.verifyStudentVerificationAuthNumber(request.email, request.authNumber, apiKeyId)

        return@coroutineScope ResponseEntity.ok(response)
    }
}