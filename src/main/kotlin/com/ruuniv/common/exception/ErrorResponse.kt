package com.ruuniv.common.exception

data class ErrorResponse(
    val message: String,
    val status: Int,
    val code: String,
) {
    constructor(
        errorCode: ErrorCode
    ) : this(
        message = errorCode.getMessageValue(),
        status = errorCode.getStatusValue(),
        code = errorCode.getCodeValue()
    )
}
