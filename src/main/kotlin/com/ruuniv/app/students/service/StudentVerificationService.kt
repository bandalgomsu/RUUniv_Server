package com.ruuniv.app.students.service

import com.ruuniv.app.mail.MailRedisKey
import com.ruuniv.app.mail.MailSender
import com.ruuniv.app.mail.MailVerifier
import com.ruuniv.app.mail.dto.MailSenderRequest
import com.ruuniv.app.students.dto.StudentVerificationResponse
import com.ruuniv.app.students.implement.StudentWriter
import com.ruuniv.app.students.model.University
import com.ruuniv.common.util.MailValidator
import com.ruuniv.common.util.RandomNumberGenerator
import kotlinx.coroutines.coroutineScope
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StudentVerificationService(
    private val studentWriter: StudentWriter,
    private val mailSender: MailSender,
    private val mailVerifier: MailVerifier
) {

    suspend fun sendStudentVerificationMail(email: String) = coroutineScope {
        MailValidator.validateEmail(email)

        mailSender.send(
            MailSenderRequest.SendAuthMailForm.createVerifyStudentForm(
                toEmail = email,
                authNumber = RandomNumberGenerator.generate()
            )
        )
    }

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheManager = "redisCacheManager", value = ["STUDENT_CACHE"], key = "#apiKeyId"),
        ]
    )
    suspend fun verifyStudentVerificationAuthNumber(
        email: String,
        authNumber: String,
        apiKeyId: Long
    ): StudentVerificationResponse.StudentVerifyResponse = coroutineScope {
        val isStudent = mailVerifier.verify(email, authNumber, MailRedisKey.SEND_VERIFY_STUDENT_MAIL)
        val university = University.createByEmail(email)

        if (isStudent) {
            studentWriter.add(apiKeyId, email, university)
        }

        StudentVerificationResponse.StudentVerifyResponse(
            isStudent = isStudent,
            universityName = university.universityName
        )
    }
}