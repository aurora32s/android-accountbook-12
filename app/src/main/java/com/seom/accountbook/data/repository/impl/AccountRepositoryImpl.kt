package com.seom.accountbook.data.repository.impl

import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.local.AccountDao
import com.seom.accountbook.data.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.data.entity.graph.OutComeByCategoryEntity
import com.seom.accountbook.data.entity.history.HistoryEntity
import com.seom.accountbook.model.graph.OutComeByCategoryModel
import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.model.history.HistoryModel
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val ioDispatcher: CoroutineDispatcher
) : AccountRepository {

    override suspend fun addAccount(account: AccountEntity) = withContext(ioDispatcher) {
        try {
            val id = accountDao.addAccount(account)

            if (id != null) Result.Success.Finish(id)
            else throw Exception("")
        } catch (exception: Exception) {
            Result.Error(exception.toString())
        }
    }

    /**
     * 특정 수입/지출 내역 요청
     */
    override suspend fun getAccount(id: Long): Result<AccountEntity> = withContext(ioDispatcher) {
        try {
            val account = accountDao.getAccount(id)

            if (account != null) Result.Success.Finish(account)
            else throw Exception("")
        } catch (exception: Exception) {
            Result.Error(exception.toString())
        }
    }

    override suspend fun updateAccount(account: AccountEntity): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val result = accountDao.updateAccount(account)

                if (result > 0) Result.Success.Finish(result)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    /**
     * 연도/월에 따른 내역 요청
     */
    override suspend fun getAllAccountByDate(year: Int, month: Int): Result<List<HistoryEntity>> =
        withContext(ioDispatcher) {
            try {
                val result = accountDao.getAllAccountByDate(year, month)
                Result.Success.Finish(result)
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    /**
     * 내역 제거 요청
     */
    override suspend fun removeAccounts(accountItems: List<Long>): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val deletedRowNum = accountDao.removeAccount(accountItems)
                Result.Success.Finish(deletedRowNum)
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    /**
     * 특정 월의 일별 총 수입/지출 내역 요청
     */
    override suspend fun getAllAccountOnDate(year: Int, month: Int): Result<List<CalendarEntity>> =
        withContext(ioDispatcher) {
            try {
                val result = accountDao.getAllAccountOnDate(year, month)
                Result.Success.Finish(result)
            } catch (exception: Exception) {
                println(exception.toString())
                Result.Error(exception.toString())
            }
        }

    override suspend fun getOutComeOnCategory(
        year: Int,
        month: Int
    ): Result<List<OutComeByCategoryEntity>> = withContext(ioDispatcher) {
        try {
            val result = accountDao.getOutComeOnCategory(year, month)
            Result.Success.Finish(result)
        } catch (exception: Exception) {
            println(exception.toString())
            Result.Error(exception.toString())
        }
    }

    override suspend fun getOutComeOnMonth(
        categoryId: Long,
        year: Int,
        month: Int
    ): Result<List<OutComeByMonth>> = withContext(ioDispatcher) {
        try {
            val result = accountDao.getOutComeOnMonth(categoryId, year, month)
            Result.Success.Finish(result)
        } catch (exception: Exception) {
            println(exception.toString())
            Result.Error(exception.toString())
        }
    }

    override suspend fun getDetailOutComeOnCategory(
        categoryId: Long,
        year: Int,
        month: Int
    ): Result<List<HistoryModel>> = withContext(ioDispatcher) {
        try {
            val result = accountDao.getDetailOutComeOnCategory(categoryId, year, month)
            Result.Success.Finish(result)
        } catch (exception: Exception) {
            println(exception.toString())
            Result.Error(exception.toString())
        }
    }
}