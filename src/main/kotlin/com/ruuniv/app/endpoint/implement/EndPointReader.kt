package com.ruuniv.app.endpoint.implement

import com.ruuniv.app.endpoint.dao.EndPointDao
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class EndPointReader(
    private val endPointDao: EndPointDao
) {

    suspend fun readAll() = coroutineScope {
        return@coroutineScope endPointDao.readAll()
    }
}