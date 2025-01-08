package com.ruuniv.common.filter

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.ruuniv.app.keys.dao.ApiKeyDao
import com.ruuniv.app.keys.exception.ApiKeyErrorCode
import com.ruuniv.common.exception.BusinessException
import com.ruuniv.common.exception.CommonErrorCode
import com.ruuniv.common.exception.ErrorCode
import com.ruuniv.common.exception.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class ApiKeyFilter(
    private val apiKeyDataAccess: ApiKeyDao,
) : WebFilter {
    companion object {
        private const val API_KEY_HEADER = "ApiKey"
        private val END_POINT = listOf(
            "/api/v1/student/verification",
        )
    }

    private val logger = LoggerFactory.getLogger(ApiKeyFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.uri.path
        if (END_POINT.none { path.startsWith(it) }) {
            return chain.filter(exchange)
        }

        val apiKey = exchange.request.headers.getFirst(API_KEY_HEADER)
        
        if (apiKey.isNullOrEmpty()) {
            return setErrorResponse(exchange, CommonErrorCode.INVALID_INPUT_VALUE)
        }

        return apiKeyDataAccess.readMono(apiKey)
            .switchIfEmpty(Mono.error(RuntimeException()))
            .flatMap {
                exchange.attributes[API_KEY_HEADER] = it.id
                chain.filter(exchange)
            }
            .onErrorResume { setErrorResponse(exchange, ApiKeyErrorCode.NOT_EXISTS_API_KEY) }
    }

    fun setErrorResponse(exchange: ServerWebExchange, errorCode: ErrorCode): Mono<Void> {
        val serverHttpResponse: ServerHttpResponse = exchange.response
        serverHttpResponse.headers.contentType = MediaType.APPLICATION_JSON
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED)

        return try {
            val body: String = ObjectMapper()
                .writeValueAsString(ErrorResponse(errorCode))
            val bytes: ByteArray = body.toByteArray(StandardCharsets.UTF_8)
            val wrap = serverHttpResponse.bufferFactory().wrap(bytes)
            logger.error("{}", errorCode.getCodeValue())

            serverHttpResponse.writeWith(Mono.just(wrap))
        } catch (e: JsonProcessingException) {
            serverHttpResponse.setComplete()
        }
    }
}
