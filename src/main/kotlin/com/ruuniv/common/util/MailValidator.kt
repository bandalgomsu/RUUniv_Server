package com.ruuniv.common.util

import com.ruuniv.common.exception.BusinessException
import com.ruuniv.common.exception.CommonErrorCode
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.stereotype.Component

@Component
class MailValidator(
) {

    companion object {
        fun validateEmail(email: String) {
            if (!EmailValidator.getInstance().isValid(email)) {
                throw BusinessException(CommonErrorCode.INVALID_EMAIL)
            }
        }
    }
}