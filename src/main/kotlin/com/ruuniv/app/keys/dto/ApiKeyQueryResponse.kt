package com.ruuniv.app.keys.dto

class ApiKeyQueryResponse {
    data class ApiKeysInfo(
        val apiKeys: List<ApiKeyInfo>
    )

    data class ApiKeyInfo(
        val apiKeyId: Long,
        val apiKey: String,
    )
}