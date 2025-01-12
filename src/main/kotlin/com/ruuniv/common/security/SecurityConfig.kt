package com.ruuniv.common.security

import JwtServerAuthenticationConverter
import com.ruuniv.common.security.exception.CustomAccessDeniedHandler
import com.ruuniv.common.security.exception.CustomAuthenticationEntryPoint
import com.ruuniv.common.security.jwt.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val jwtService: JwtService,
    private val userDetailService: UserDetailService
) {

    @Bean
    fun filterChain(
        http: ServerHttpSecurity,
    ): SecurityWebFilterChain {
        val filter = AuthenticationWebFilter(reactiveAuthenticationManager())
        filter.setServerAuthenticationConverter(serverAuthenticationConverter())
        filter.setAuthenticationFailureHandler(ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint()))

        return http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .sessionManagement { it.ConcurrentSessionsSpec() }
            .headers { it.frameOptions { it.disable() } }
            .authorizeExchange {
                it.pathMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                it.pathMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
                it.pathMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                it.pathMatchers(HttpMethod.POST, "/api/v1/students/**").permitAll()
                it.pathMatchers(HttpMethod.GET, "/api/v1/students/**").permitAll()
                it.pathMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                it.pathMatchers(HttpMethod.GET, "/health").permitAll()
                it.anyExchange().authenticated()
            }
            .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
            .exceptionHandling {
                it.accessDeniedHandler(accessDeniedHandler())
                it.authenticationEntryPoint(authenticationEntryPoint())
            }
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun reactiveAuthenticationManager(
    ): ReactiveAuthenticationManager {
        return JwtAuthenticationManager(jwtService, userDetailService)
    }

    @Bean
    fun serverAuthenticationConverter(): ServerAuthenticationConverter {
        return JwtServerAuthenticationConverter()
    }

    @Bean
    fun authenticationEntryPoint(): ServerAuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }

    @Bean
    fun accessDeniedHandler(): CustomAccessDeniedHandler {
        return CustomAccessDeniedHandler()
    }
}