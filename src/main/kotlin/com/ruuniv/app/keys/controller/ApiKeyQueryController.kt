package com.ruuniv.app.keys.controller

import com.ruuniv.app.keys.dto.ApiKeyQueryResponse
import com.ruuniv.app.keys.service.ApiKeyQueryService
import com.ruuniv.common.security.CustomUserDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.coroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "API_KEY_조회")
@RestController
@RequestMapping("/api/v1/apiKeys")
class ApiKeyQueryController(
    private val apiKeyQueryService: ApiKeyQueryService
) {

    @GetMapping("/me")
    @Operation(summary = "나의 API 키를 조회", description = "나의 API 키를 조회 합니다")
    suspend fun getApiKeysByUserId(
        @AuthenticationPrincipal principal: CustomUserDetails,
    ): ResponseEntity<ApiKeyQueryResponse.ApiKeysInfo> = coroutineScope {
        val response = apiKeyQueryService.getApiKeysByUserId(principal.getId())

        return@coroutineScope ResponseEntity.ok(response)
    }
}