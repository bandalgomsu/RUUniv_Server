package com.ruuniv.app.endpoint.dao

import com.ruuniv.app.endpoint.model.EndPoint

interface EndPointDao {
    suspend fun readAll(): List<EndPoint>
    suspend fun add(endPoint: EndPoint)
    suspend fun delete(endPoint: EndPoint)
    suspend fun delete(endPoint: String)
}