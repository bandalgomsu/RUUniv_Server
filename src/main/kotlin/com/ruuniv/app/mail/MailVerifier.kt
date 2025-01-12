package com.ruuniv.app.mail

import com.ruuniv.app.mail.exception.MailErrorCode
import com.ruuniv.common.exception.BusinessException
import com.ruuniv.common.redis.RedisClient
import com.ruuniv.common.redis.RedisKey
import com.ruuniv.common.util.MailValidator
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class MailVerifier(
    private val redisClient: RedisClient,
) {

    suspend fun verify(email: String, inputAuthNumber: String, redisKey: RedisKey): Boolean = coroutineScope {
        MailValidator.validateEmail(email)

        val key = redisKey.combine(email)

        val authNumber = redisClient.getData(key, String::class)
            ?: throw BusinessException(MailErrorCode.NOT_EXISTS_AUTH_NUMBER)

        if (authNumber == inputAuthNumber) {
            redisClient.deleteData(key)

            return@coroutineScope true
        }

        return@coroutineScope false
    }
}