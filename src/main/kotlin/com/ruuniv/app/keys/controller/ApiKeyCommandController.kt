package com.ruuniv.app.keys.controller

import com.ruuniv.app.keys.service.ApiKeyCommandService
import com.ruuniv.common.security.CustomUserDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.coroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "API_KEY_명령")
@RestController
@RequestMapping("/api/v1/apiKeys")
class ApiKeyCommandController(
    private val apiKeyCommandService: ApiKeyCommandService,
) {

    @Operation(summary = "API_KEY 생성", description = "API_KEY 를 생성 합니다")
    @PostMapping("/me")
    suspend fun createApiKey(@AuthenticationPrincipal principal: CustomUserDetails): ResponseEntity<Void> =
        coroutineScope {
            apiKeyCommandService.createApiKey(principal.getId())

            return@coroutineScope ResponseEntity.ok().build()
        }

    @Operation(summary = "API_KEY 삭제", description = "API_KEY 를 삭제 합니다")
    @DeleteMapping("/me/{apiKeyId}")
    suspend fun deleteApiKey(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable apiKeyId: Long
    ): ResponseEntity<Void> =
        coroutineScope {
            apiKeyCommandService.deleteApiKey(apiKeyId, principal.getId())

            return@coroutineScope ResponseEntity.ok().build()
        }
}