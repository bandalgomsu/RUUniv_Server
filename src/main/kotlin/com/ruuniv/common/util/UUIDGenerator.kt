package com.ruuniv.common.util

import java.util.*

class UUIDGenerator {
    companion object {
        fun generateUUID(): String {
            return UUID.randomUUID().toString()
        }
    }
}