package com.seom.accountbook.data.repository

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.entity.calendar.CalendarEntity
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

    // 특정 수입/지출 내역 제거
    suspend fun removeAccounts(accountItems: List<Long>): Result<Int>

    // 일별 수입/지출 내역 가져오기
    suspend fun getAllAccountOnDate(year: Int, month: Int): Result<List<CalendarEntity>>
}