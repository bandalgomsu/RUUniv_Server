package com.ruuniv.common.security.exception

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.ruuniv.common.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class CustomAuthenticationEntryPoint : ServerAuthenticationEntryPoint {

    override fun commence(exchange: ServerWebExchange?, ex: AuthenticationException?): Mono<Void> {
        val serverHttpResponse: ServerHttpResponse = exchange!!.response
        serverHttpResponse.headers.contentType = MediaType.APPLICATION_JSON
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED)

        return try {
            val body: String = ObjectMapper()
                .writeValueAsString(ErrorResponse(AuthErrorCode.UNAUTHORIZED))
            val bytes: ByteArray = body.toByteArray(StandardCharsets.UTF_8)
            val wrap = serverHttpResponse.bufferFactory().wrap(bytes)

            serverHttpResponse.writeWith(Mono.just(wrap))
        } catch (e: JsonProcessingException) {
            serverHttpResponse.setComplete()
        }
    }
}