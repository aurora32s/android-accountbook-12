package com.seom.accountbook.usecase

import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.model.post.PostModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetPostDataUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val getAllSettingDataUseCase: GetAllSettingDataUseCase
) {
    suspend operator fun invoke(id: Long?): PostModel =
        coroutineScope {
            val account =
                id?.let { async { accountRepository.getAccount(id) } } ?: kotlin.run { null }
            val settings = async { getAllSettingDataUseCase() }

            PostModel(
                account = account?.await(),
                settingModel = settings.await()
            )
        }
}