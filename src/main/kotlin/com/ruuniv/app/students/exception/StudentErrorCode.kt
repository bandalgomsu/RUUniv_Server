package com.ruuniv.app.students.exception

import com.ruuniv.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class StudentErrorCode(val code: String, val message: String, var status: Int) : ErrorCode {

    NOT_EXISTS_STUDENT("S01", "NOT_EXISTS_STUDENT", HttpStatus.BAD_REQUEST.value()),
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