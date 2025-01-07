package com.ruuniv.infrastricture.dao.repository.students

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface StudentCoroutineRepository : CoroutineCrudRepository<StudentEntity, Long> {
    suspend fun findAllByApiKeyId(apiKeyId: Long): Flow<StudentEntity>
}