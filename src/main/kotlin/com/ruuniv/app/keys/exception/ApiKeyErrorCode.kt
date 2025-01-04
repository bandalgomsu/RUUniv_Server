package com.ruuniv.app.keys.exception

import com.ruuniv.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class ApiKeyErrorCode(val code: String, val message: String, var status: Int) : ErrorCode {

    NOT_EXISTS_API_KEY("AP01", "NOT_EXISTS_API_KEY", HttpStatus.BAD_REQUEST.value()),
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