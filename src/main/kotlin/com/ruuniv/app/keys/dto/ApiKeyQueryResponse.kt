package com.ruuniv.app.keys.dto

import com.fasterxml.jackson.annotation.JsonProperty

class ApiKeyQueryResponse {
    data class ApiKeysInfo(
        @JsonProperty("apiKeys") val apiKeys: List<ApiKeyInfo>
    )

    data class ApiKeyInfo(
        @JsonProperty("apiKeyId") val apiKeyId: Long,
        @JsonProperty("apiKey") val apiKey: String,
    )
}