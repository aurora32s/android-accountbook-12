package com.seom.accountbook.data.repository

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.category.CategoryEntity

interface CategoryRepository {
    // 수입/지출 카테고리 추가
    suspend fun addCategory(category: CategoryEntity): Result<Long>

    // 수입/지출 카테고리 업데이트
    suspend fun updateCategory(category: CategoryEntity): Result<Int>

    // 특정 수입/지출 카테고리 요청
    suspend fun getCategory(id: Long): Result<CategoryEntity>

    // 모든 수입/지출 카테고리 요청
    suspend fun getAllCategories(): Result<List<CategoryEntity>>
}