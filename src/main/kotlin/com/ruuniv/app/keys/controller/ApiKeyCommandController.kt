package com.ruuniv.app.keys.controller

import com.ruuniv.app.keys.service.ApiKeyCommandService
import com.ruuniv.common.security.CustomUserDetails
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.coroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "API KEY COMMAND CONTROLLER", description = "API KEY COMMAND CONTROLLER")
@RestController
@RequestMapping("/api/v1/apiKeys")
class ApiKeyCommandController(
    private val apiKeyCommandService: ApiKeyCommandService,
) {

    @PostMapping("/me")
    suspend fun createApiKey(@AuthenticationPrincipal principal: CustomUserDetails): ResponseEntity<Void> =
        coroutineScope {
            apiKeyCommandService.createApiKey(principal.getId())

            return@coroutineScope ResponseEntity.ok().build()
        }

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