package com.ruuniv.app.mail

import com.ruuniv.app.mail.exception.MailErrorCode
import com.ruuniv.common.exception.BusinessException
import com.ruuniv.common.util.MailValidator
import com.ruuniv.infrastricture.redis.RedisClient
import com.ruuniv.infrastricture.redis.RedisKey
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class MailVerifier(
    private val redisClient: RedisClient
) {

    suspend fun verify(email: String, inputAuthNumber: String, redisKey: RedisKey): Boolean = coroutineScope {
        MailValidator.validateEmail(email)

        val authNumber = redisClient.getData(redisKey.combine(email), String::class)
            ?: throw BusinessException(MailErrorCode.NOT_EXISTS_AUTH_NUMBER)

        authNumber == inputAuthNumber
    }
}