package com.ruuniv.common.filter

import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class RequestResponseLoggingFilter : WebFilter {
    companion object {
        private const val API_KEY_HEADER = "ApiKey"
        private val NOT_LOGGING_END_POINT = listOf(
            "/swagger-ui/",
        )
    }

    private val logger = LoggerFactory.getLogger(RequestResponseLoggingFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.uri.path

        if (NOT_LOGGING_END_POINT.any { path.startsWith(it) }) {
            return chain.filter(exchange)
        }

        val traceId = UUID.randomUUID().toString()

        val startTime = Instant.now()

        logRequest(exchange.request, traceId)

        return chain.filter(exchange)
            .doOnSuccess {
                val elapsedTime = Duration.between(startTime, Instant.now()).toMillis()
                logResponse(exchange.response, elapsedTime, traceId)
            }
    }

    private fun logRequest(request: ServerHttpRequest, traceId: String) {
        val method = request.method.toString()
        val uri = request.uri
        val clientIp = request.remoteAddress?.address?.hostAddress ?: "Unknown"

        val logMessage = StringBuilder("[REQUEST]")
            .append(" TraceId: $traceId")
            .append(" Method: $method")
            .append(", URI: $uri")
            .append(", ClientIP: $clientIp")

        logger.info(logMessage.toString())
    }

    private fun logResponse(response: ServerHttpResponse, elapsedTime: Long, traceId: String) {
        val statusCode = response.statusCode

        val logMessage = StringBuilder("[RESPONSE]")
            .append(" TraceId: $traceId")
            .append(" StatusCode: $statusCode")
            .append(", ElapsedTime: ${elapsedTime}ms")

        logger.info(logMessage.toString())
    }
}
