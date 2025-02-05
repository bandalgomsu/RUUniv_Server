package com.ruuniv.common.cache

import org.springframework.stereotype.Component

@Component
class RedisCacheEnableState(
    var isRedisCacheEnable: Boolean = true
) {

}