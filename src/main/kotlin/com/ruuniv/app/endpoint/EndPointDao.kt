package com.ruuniv.app.endpoint

interface EndPointDao {
    suspend fun readAll(): List<EndPoint>
    suspend fun add(endPoint: EndPoint)
}