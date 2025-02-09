package com.ruuniv.infrastricture.dao.repository.endpoint

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EndPointCoroutineRepository : CoroutineCrudRepository<EndPointEntity, Long> {

    suspend fun deleteByEndPoint(endPoint: String)
}