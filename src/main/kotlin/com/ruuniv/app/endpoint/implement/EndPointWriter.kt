package com.ruuniv.app.endpoint.implement

import com.ruuniv.app.endpoint.dao.EndPointDao
import com.ruuniv.app.endpoint.model.EndPoint
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EndPointWriter(
    private val endPointDao: EndPointDao
) {

    @Transactional
    suspend fun add(endPoint: String) = coroutineScope {
        endPointDao.add(
            EndPoint(
                endPoint = endPoint
            )
        )
    }

    @Transactional
    suspend fun delete(endPoint: EndPoint) = coroutineScope {
        endPointDao.delete(endPoint)
    }

    @Transactional
    suspend fun delete(endPoint: String) = coroutineScope {
        endPointDao.delete(endPoint)
    }
}