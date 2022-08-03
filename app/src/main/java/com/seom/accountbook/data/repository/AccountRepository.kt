package com.seom.accountbook.data.repository

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.data.entity.graph.OutComeByCategoryEntity
import com.seom.accountbook.data.entity.history.HistoryEntity
import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.model.history.HistoryModel

interface AccountRepository {
    // 수입/지출 내역 추가
    suspend fun addAccount(account: AccountEntity): Result<Long>

    // 특정 수입/지출 내역 요청
    suspend fun getAccount(id: Long): Result<AccountEntity>

    // 특정 수입/지출 내역 업데이트
    suspend fun updateAccount(account: AccountEntity): Result<Int>

    // 월별 수입/지출 내역 요청
    suspend fun getAllAccountByDate(year: Int, month: Int): Result<List<HistoryEntity>>

    // 특정 수입/지출 내역 제거
    suspend fun removeAccounts(accountItems: List<Long>): Result<Int>

    // 일별 수입/지출 내역 가져오기
    suspend fun getAllAccountOnDate(year: Int, month: Int): Result<List<CalendarEntity>>

    // 특정 월의 카테고리별 지출 내역 가져오기
    suspend fun getOutComeOnCategory(year: Int, month: Int): Result<List<OutComeByCategoryEntity>>

    // 특정 카테고리의 6개월 이내 월별 지출 총액 가져오기
    suspend fun getOutComeOnMonth(categoryId: Long, year: Int, month: Int): Result<List<OutComeByMonth>>

    // 특정 카테고리의 6개월 이내 일별 지출 내역 가져오기
    suspend fun getDetailOutComeOnCategory(categoryId: Long, year: Int, month: Int): Result<List<HistoryModel>>
}