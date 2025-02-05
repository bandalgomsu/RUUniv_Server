package com.ruuniv.infrastricture.redis

import com.ruuniv.common.cache.RedisCacheEnableState
import io.lettuce.core.event.connection.ConnectedEvent
import io.lettuce.core.event.connection.DisconnectedEvent
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.stereotype.Component

@Component
class LettuceRedisConnectionSubscriber(
    private val lettuceConnectionFactory: LettuceConnectionFactory,
    private val redisCacheEnableState: RedisCacheEnableState,
) {

    private val eventBus = lettuceConnectionFactory.clientResources!!.eventBus()
    private val log = LoggerFactory.getLogger(LettuceRedisConnectionSubscriber::class.java)

    @PostConstruct
    fun init() {
        subscribeRedisConnectEvents()
    }

    private fun subscribeRedisConnectEvents() {
        eventBus
            .get()
            .subscribe {
                when (it) {
                    is ConnectedEvent -> {
                        if (!redisCacheEnableState.isRedisCacheEnable) {
                            redisCacheEnableState.isRedisCacheEnable = true
                        }

                        log.info("IS REDIS CACHE ENABLE State = {}", redisCacheEnableState.isRedisCacheEnable)
                    }

                    is DisconnectedEvent -> {
                        if (redisCacheEnableState.isRedisCacheEnable) {
                            redisCacheEnableState.isRedisCacheEnable = false
                        }

                        log.info("IS REDIS CACHE DISABLE State = {}", redisCacheEnableState.isRedisCacheEnable)
                    }
                }
            }
    }

}