package com.ruuniv.app.endpoint.implement

import org.springframework.stereotype.Component

/**
 * 현재 인스턴스의 EndPoint
 */
@Component
class SelfEndPointStorage(
    var selfEndPoint: String? = null
) {

}