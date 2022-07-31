package com.seom.accountbook.data.repository

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.method.MethodEntity

interface MethodRepository {
    // 수입/지출 카테고리 추가
    suspend fun addMethod(method: MethodEntity): Result<Long>

    // 수입/지출 카테고리 업데이트
    suspend fun updateMethod(method: MethodEntity): Result<Int>

    // 특정 수입/지출 카테고리 요청
    suspend fun getMethod(id: Long): Result<MethodEntity>

    // 모든 수입/지출 카테고리 요청
    suspend fun getAllMethods(): Result<List<MethodEntity>>
}