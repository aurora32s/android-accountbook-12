package com.seom.accountbook.data.repository.impl

import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.local.CategoryDao
import com.seom.accountbook.data.repository.CategoryRepository
import com.seom.accountbook.di.provideCategoryDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.seom.accountbook.data.entity.Result

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao = provideCategoryDao(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {
    override suspend fun addCategory(category: CategoryEntity): Result<Long> =
        withContext(ioDispatcher) {
            try {
                val categoryId = categoryDao.addCategory(category)

                if (categoryId != null) Result.Success(categoryId)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun updateCategory(category: CategoryEntity): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val result = categoryDao.updateCategory(category)

                if (result > 0) Result.Success(result)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun getCategory(id: Long): Result<CategoryEntity> =
        withContext(ioDispatcher) {
            try {
                val category = categoryDao.getCategory(id)

                if (category != null) Result.Success(category)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun getAllCategories(): Result<List<CategoryEntity>> =
        withContext(ioDispatcher) {
            try {
                val categories = categoryDao.getAllCategory()

                if (categories.size > 0) Result.Success(categories)
                else throw Exception()
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }
}