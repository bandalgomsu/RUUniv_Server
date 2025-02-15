package com.ruuniv.app.students.exception

import com.ruuniv.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class UniversityErrorCode(val code: String, val message: String, var status: Int) : ErrorCode {

    NOT_SUPPORTED_UNIVERSITY("U01", "NOT_SUPPORTED_UNIVERSITY", HttpStatus.BAD_REQUEST.value()),
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