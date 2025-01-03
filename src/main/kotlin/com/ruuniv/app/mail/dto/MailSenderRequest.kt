package com.ruuniv.app.mail.dto

import com.ruuniv.app.mail.MailRedisKey
import com.ruuniv.common.util.MailValidator

private const val VERIFY_STUDENT_FORM_DEFAULT_TITLE = "학생 인증 메일 입니다"


class MailSenderRequest {

    data class SendAuthMailForm(
        val toEmail: String,
        val title: String,
        val content: String,
        val authNumber: String,
        val redisKey: String
    ) {
        companion object {
            fun createVerifyStudentForm(
                toEmail: String,
                authNumber: String,
                title: String = VERIFY_STUDENT_FORM_DEFAULT_TITLE,
                content: String = "대학생 메일 인증 번호는 $authNumber 입니다. </br> 인증번호를 제대로 입력해주세요",
            ): SendAuthMailForm {
                MailValidator.validateEmail(toEmail)

                return SendAuthMailForm(
                    toEmail = toEmail,
                    title = title,
                    content = content,
                    authNumber = authNumber,
                    redisKey = MailRedisKey.SEND_VERIFY_STUDENT_MAIL.combine(toEmail),
                )
            }
        }
    }
}