package com.seom.accountbook.data.repository.impl

import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.local.AccountDao
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.di.provideAccountDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.seom.accountbook.data.entity.Result

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
}