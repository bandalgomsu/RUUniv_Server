package com.ruuniv.app.auth.dto

class AuthResponse {
    data class TokenResponse(
        val accessToken: String,
        val refreshToken: String
    ) {

    }
}