package com.ruuniv.common.util

import java.util.*

class RandomNumberGenerator {
    companion object {
        fun generate(): String {
            val random = Random()
            var randomNumber = ""

            for (i in 0..5) {
                randomNumber += random.nextInt(10).toString()
            }

            return randomNumber
        }
    }
}