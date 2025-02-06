package com.ruuniv.app.endpoint

import java.time.LocalDateTime

class EndPoint(
    var id: Long? = null,
    var endPoint: String,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
) {

}