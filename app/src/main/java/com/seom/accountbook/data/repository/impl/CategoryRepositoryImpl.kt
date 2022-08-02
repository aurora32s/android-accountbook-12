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

                if (categoryId != null) {
                    if (categoryId == -1L) Result.Success.Pause
                    else Result.Success.Finish(categoryId)
                } else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun updateCategory(category: CategoryEntity): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val result = categoryDao.updateCategory(category)

                if (result > 0) Result.Success.Finish(result)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun getCategory(id: Long): Result<CategoryEntity> =
        withContext(ioDispatcher) {
            try {
                val category = categoryDao.getCategory(id)

                if (category != null) Result.Success.Finish(category)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun getAllCategories(): Result<List<CategoryEntity>> =
        withContext(ioDispatcher) {
            try {
                val categories = categoryDao.getAllCategory()
                Result.Success.Finish(categories)
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }
}