package com.ruuniv.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val jwt = "JWT"
        val apiKey = "ApiKey"

        val securityRequirement = SecurityRequirement()
            .addList(jwt)
            .addList(apiKey)

        val components = Components()
            .addSecuritySchemes(
                jwt, SecurityScheme()
                    .name(jwt)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )
            .addSecuritySchemes(
                apiKey, SecurityScheme()
                    .name(apiKey)
                    .type(SecurityScheme.Type.APIKEY)
                    .`in`(SecurityScheme.In.HEADER)
            )

        return OpenAPI()
            .addServersItem(Server().url("/"))
            .components(components) // components 한 번만 추가
            .info(apiInfo())
            .addSecurityItem(securityRequirement)
    }

    private fun apiInfo(): Info {
        return Info()
            .title("LOL_CHECK") // API의 제목
            .description("LOL_CHECK") // API에 대한 설명
            .version("1.0.0") // API의 버전
    }
}


