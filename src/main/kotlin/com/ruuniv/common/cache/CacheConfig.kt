package com.ruuniv.common.cache

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Caffeine
import com.ruuniv.common.redis.RedisPublisher
import org.springframework.cache.annotation.AnnotationCacheOperationSource
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.cache.interceptor.CacheOperationSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class CacheConfig(
    private val redisPublisher: RedisPublisher,
    private val objectMapper: ObjectMapper,
) {
    @Bean("redisCacheManager")
    fun redisCacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        val cacheConfigurations = CacheType.entries.associate { cacheType ->
            cacheType.cacheName to RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(cacheType.expireAfterWrite))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(
                        GenericJackson2JsonRedisSerializer()
                    )
                )
        }

        return RedisCacheManager.builder(redisConnectionFactory)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build()
    }

    @Primary
    @Bean("caffeineCacheManager")
    fun caffeineCacheManager(): CaffeineCacheManager {
        val caffeineCacheManager = CaffeineCacheManager()

        caffeineCacheManager.isAllowNullValues = true

        CacheType.entries.forEach {
            val caffeineCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(it.expireAfterWrite))
                .maximumSize(it.maxSize)
                .build<Any, Any>()

            caffeineCacheManager
                .registerCustomCache(it.name, caffeineCache)
        }

        return caffeineCacheManager
    }


    @Bean
    fun cacheInterceptor(): CacheInterceptor {
        val interceptor = CustomCacheInterceptor(caffeineCacheManager(), redisPublisher)

        interceptor.setCacheOperationSources(cacheOperationSource())

        return interceptor
    }

    @Bean
    fun cacheOperationSource(): CacheOperationSource {
        return AnnotationCacheOperationSource()
    }
}