package com.ruuniv.app.mail

import com.ruuniv.app.mail.dto.MailSenderRequest
import com.ruuniv.common.redis.RedisClient
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class MailSender(
    private val mailSender: JavaMailSender,
    private val redisClient: RedisClient,
    @Value("\${spring.mail.username}")
    private val fromEmail: String,
) {

    suspend fun send(form: MailSenderRequest.SendAuthMailForm) = coroutineScope {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "utf-8")

        helper.setFrom(fromEmail)
        helper.setTo(form.toEmail)
        helper.setSubject(form.title)
        helper.setText(form.content, true)

        CoroutineScope(Dispatchers.IO + Job()).launch { mailSender.send(message) }
        CoroutineScope(Dispatchers.IO + Job()).launch {
            redisClient.setData(form.redisKey, form.authNumber, 180)
        }
    }
}