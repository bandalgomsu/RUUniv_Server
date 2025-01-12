package com.ruuniv.app.mail

import com.ruuniv.common.redis.RedisKey

enum class MailRedisKey(val key: String) : RedisKey {
    SEND_VERIFY_STUDENT_MAIL("SEND_VERIFY_STUDENT_MAIL");

    override fun combine(target: String): String {
        return "${this.key}:${target}"
    }
}