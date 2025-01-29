package com.ruuniv.app.keys.dto

import java.io.Serializable

class ApiKeyQueryResponse {
    data class ApiKeysInfo(
        val apiKeys: List<ApiKeyInfo>
    ) : Serializable

    data class ApiKeyInfo(
        val apiKeyId: Long,
        val apiKey: String,
    ) : Serializable
}