package com.seom.accountbook.usecase

import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.model.detail.DetailOutComeOnCategory
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetDetailOutComeOnCategoryUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(categoryId: Long, year: Int, month: Int): DetailOutComeOnCategory =
        coroutineScope {
            val outComeOnMonth =
                async { accountRepository.getOutComeOnMonth(categoryId, year, month) }
            val accounts =
                async { accountRepository.getDetailOutComeOnCategory(categoryId, year, month) }

            DetailOutComeOnCategory(
                outComeOnMonth = outComeOnMonth.await(),
                accounts = accounts.await()
            )
        }
}