package com.ruuniv.common.security

import JwtToken
import com.ruuniv.common.security.exception.CustomAuthenticationException
import com.ruuniv.common.security.jwt.JwtService
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

class JwtAuthenticationManager(
    private val jwtService: JwtService,
    private val users: UserDetailService
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
            .filter { auth -> auth is JwtToken }
            .cast(JwtToken::class.java)
            .flatMap { validate(it) }
    }

    private fun validate(token: JwtToken): Mono<Authentication> {
        return jwtService.validate(token)
            .onErrorResume {
                Mono.error(CustomAuthenticationException(it.message))
            }
            .then(Mono.defer {
                jwtService.getEmail(token)
                    .flatMap { users.findByUsername(it) }
                    .map {
                        UsernamePasswordAuthenticationToken(
                            it,
                            null,
                            it.authorities
                        )
                    }
            })
    }
}