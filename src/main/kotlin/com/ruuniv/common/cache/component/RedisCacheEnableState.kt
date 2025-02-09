package com.ruuniv.common.cache.component

import org.springframework.stereotype.Component

@Component
class RedisCacheEnableState(
    var isRedisCacheEnable: Boolean = true
) {

}