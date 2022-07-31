package com.seom.accountbook.data.repository

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.account.AccountEntity

interface AccountRepository {
    // 수입/지출 내역 추가
    suspend fun addAccount(account: AccountEntity): Result<Long>
}