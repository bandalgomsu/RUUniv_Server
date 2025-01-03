package com.ruuniv.common.exception

interface ErrorCode {

    fun getCodeValue(): String
    fun getStatusValue(): Int
    fun getMessageValue(): String
}