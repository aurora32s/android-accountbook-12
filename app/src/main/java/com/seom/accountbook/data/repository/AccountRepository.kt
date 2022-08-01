package com.seom.accountbook.data.repository

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.model.history.HistoryModel

interface AccountRepository {
    // 수입/지출 내역 추가
    suspend fun addAccount(account: AccountEntity): Result<Long>

    // 특정 수입/지출 내역 요청
    suspend fun getAccount(id: Long): Result<AccountEntity>

    // 특정 수입/지출 내역 업데이트
    suspend fun updateAccount(account: AccountEntity): Result<Int>

    // 월별 수입/지출 내역 요청
    suspend fun getAllAccountByDate(year: Int, month: Int): Result<List<HistoryModel>>
}