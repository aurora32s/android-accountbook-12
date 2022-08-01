package com.seom.accountbook.data.repository.impl

import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.local.AccountDao
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.di.provideAccountDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.model.graph.OutComeByCategory
import com.seom.accountbook.model.history.HistoryModel

class AccountRepositoryImpl(
    private val accountDao: AccountDao = provideAccountDao(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AccountRepository {

    override suspend fun addAccount(account: AccountEntity) = withContext(ioDispatcher) {
        try {
            val id = accountDao.addAccount(account)

            if (id != null) Result.Success(id)
            else throw Exception("")
        } catch (exception: Exception) {
            Result.Error(exception.toString())
        }
    }

    override suspend fun getAccount(id: Long): Result<AccountEntity> = withContext(ioDispatcher) {
        try {
            val account = accountDao.getAccount(id)

            if (account != null) Result.Success(account)
            else throw Exception("")
        } catch (exception: Exception) {
            Result.Error(exception.toString())
        }
    }

    override suspend fun updateAccount(account: AccountEntity): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val result = accountDao.updateAccount(account)

                if (result > 0) Result.Success(result)
                else throw Exception("")
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun getAllAccountByDate(year: Int, month: Int): Result<List<HistoryModel>> =
        withContext(ioDispatcher) {
            try {
                val result = accountDao.getAllAccountByDate(year, month)
                Result.Success(result)
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun removeAccounts(accountItems: List<Long>): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val deletedRowNum = accountDao.removeAccount(accountItems)
                Result.Success(deletedRowNum)
            } catch (exception: Exception) {
                Result.Error(exception.toString())
            }
        }

    override suspend fun getAllAccountOnDate(year: Int, month: Int): Result<List<CalendarEntity>> =
        withContext(ioDispatcher) {
            try {
                val result = accountDao.getAllAccountOnDate(year, month)
                Result.Success(result)
            } catch (exception: Exception) {
                println(exception.toString())
                Result.Error(exception.toString())
            }
        }

    override suspend fun getOutComeOnCategory(
        year: Int,
        month: Int
    ): Result<List<OutComeByCategory>> = withContext(ioDispatcher) {
        try {
            val result = accountDao.getOutComeOnCategory(year, month)
            Result.Success(result)
        } catch (exception: Exception) {
            println(exception.toString())
            Result.Error(exception.toString())
        }
    }
}