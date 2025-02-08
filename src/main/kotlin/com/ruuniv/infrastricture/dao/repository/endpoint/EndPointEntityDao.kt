package com.ruuniv.infrastricture.dao.repository.endpoint

import com.ruuniv.app.endpoint.dao.EndPointDao
import com.ruuniv.app.endpoint.model.EndPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository

@Repository
class EndPointEntityDao(
    private val repository: EndPointCoroutineRepository
) : EndPointDao {

    override suspend fun readAll(): List<EndPoint> = coroutineScope {
        return@coroutineScope repository.findAll().map {
            EndPoint(
                id = it.id,
                endPoint = it.endPoint,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.toList()
    }

    override suspend fun add(endPoint: EndPoint): Unit = coroutineScope {
        repository.save(
            EndPointEntity(
                id = endPoint.id,
                endPoint = endPoint.endPoint
            )
        )
    }

    override suspend fun delete(endPoint: EndPoint): Unit = coroutineScope {
        repository.delete(
            EndPointEntity(
                id = endPoint.id,
                endPoint = endPoint.endPoint,
            )
        )
    }

    override suspend fun delete(endPoint: String) {
        repository.deleteByEndPoint(endPoint)
    }
}