package com.ruuniv.app.mail.exception

import com.ruuniv.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class MailErrorCode(val code: String, val message: String, var status: Int) : ErrorCode {

    NOT_EXISTS_AUTH_NUMBER("M01", "NOT_EXISTS_AUTH_NUMBER", HttpStatus.BAD_REQUEST.value()),
    ;

    override fun getCodeValue(): String {
        return this.code
    }

    override fun getStatusValue(): Int {
        return this.status
    }

    override fun getMessageValue(): String {
        return this.message
    }
}