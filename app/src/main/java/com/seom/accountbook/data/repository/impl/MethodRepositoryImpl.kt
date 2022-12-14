package com.seom.accountbook.data.repository.impl

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.data.local.MethodDao
import com.seom.accountbook.data.repository.MethodRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MethodRepositoryImpl(
    private val methodDao: MethodDao,
    private val ioDispatcher: CoroutineDispatcher
) : MethodRepository {
    override suspend fun addMethod(method: MethodEntity): Result<Long> =
        withContext(ioDispatcher)
        {
            try {
                val methodId = methodDao.addMethod(method)

                if (methodId != null) {
                    if (methodId == -1L) Result.Success.Pause
                    else Result.Success.Finish(methodId)
                } else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun updateMethod(method: MethodEntity): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val result = methodDao.updateMethod(method)

                if (result > 0) Result.Success.Finish(result)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun getMethod(id: Long): Result<MethodEntity> =
        withContext(ioDispatcher) {
            try {
                val category = methodDao.getMethods(id)

                if (category != null) Result.Success.Finish(category)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun getAllMethods(): Result<List<MethodEntity>> =
        withContext(ioDispatcher) {
            try {
                val methods = methodDao.getAllMethods()
                Result.Success.Finish(methods)
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }
}