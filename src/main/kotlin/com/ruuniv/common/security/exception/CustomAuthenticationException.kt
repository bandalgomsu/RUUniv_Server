package com.ruuniv.common.security.exception

import org.springframework.security.core.AuthenticationException

class CustomAuthenticationException(message: String?) : AuthenticationException(message) {
}