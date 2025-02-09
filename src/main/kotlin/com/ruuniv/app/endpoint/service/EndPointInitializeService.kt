package com.ruuniv.app.endpoint.service

import com.ruuniv.app.endpoint.implement.EndPointWriter
import com.ruuniv.app.endpoint.implement.SelfEndPointStorage
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.InetAddress
import java.net.URL

/**
 * WAS 실행시 자신의 EndPoint 저장을 위한 서비스
 *
 */
@Service
class EndPointInitializeService(
    private val endPointWriter: EndPointWriter,
    private val selfEndPointStorage: SelfEndPointStorage,
    @Value("\${server.port}")
    private val port: String,
) {
    private val log = LoggerFactory.getLogger(EndPointInitializeService::class.java)

    @PostConstruct
    fun initializeEndPoint1() {
        runBlocking {
            val ip = try {
                URL("http://169.254.169.254/latest/meta-data/local-ipv4")
                    .readText()
                    .trim()
            } catch (e: Exception) {
                InetAddress.getLocalHost().hostAddress
            }

            val endPoint = "$ip:$port"

            endPointWriter.add(endPoint)
            selfEndPointStorage.selfEndPoint = endPoint
            log.info("SELF_END_POINT : {}", selfEndPointStorage.selfEndPoint)
        }

    }

    @PreDestroy
    fun destroyEndPoint() {
        runBlocking {
            val endPoint = selfEndPointStorage.selfEndPoint!!

            endPointWriter.delete(endPoint)
        }
    }
}