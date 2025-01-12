package com.ruuniv.app.students.controller

import com.ruuniv.app.students.dto.StudentQueryResponse
import com.ruuniv.app.students.service.StudentQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.coroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@Tag(name = "학생_조회")
@RestController
@RequestMapping("/api/v1/students")
class StudentQueryController(
    private val studentQueryService: StudentQueryService,
) {

    @GetMapping
    @Operation(summary = "해당 API 키 에서  인증된 학생 조회", description = "해당 API 키 에서  인증된 학생을 조회 합니다")
    suspend fun getStudentsByApiKeyId(
        exchange: ServerWebExchange,
    ): ResponseEntity<StudentQueryResponse.StudentsInfo> = coroutineScope {
        val response = studentQueryService.getStudentsByApiKeyId(exchange.getAttribute<Long>("ApiKey")!!)

        return@coroutineScope ResponseEntity.ok(response)
    }
}