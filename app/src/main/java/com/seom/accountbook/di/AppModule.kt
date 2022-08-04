package com.seom.accountbook.di

import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.CategoryRepository
import com.seom.accountbook.data.repository.MethodRepository
import com.seom.accountbook.usecase.GetAllSettingDataUseCase
import com.seom.accountbook.usecase.GetDetailOutComeOnCategoryUseCase
import com.seom.accountbook.usecase.GetPostDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * use case
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetAllSettingDataUseCase(
        methodRepository: MethodRepository,
        categoryRepository: CategoryRepository
    ): GetAllSettingDataUseCase {
        return GetAllSettingDataUseCase(methodRepository, categoryRepository)
    }

    @Provides
    fun provideGetDetailOutComeOnCategoryUseCase(
        accountRepository: AccountRepository
    ): GetDetailOutComeOnCategoryUseCase {
        return GetDetailOutComeOnCategoryUseCase(accountRepository)
    }

    @Provides
    fun provideGetPostDataUseCase(
        accountRepository: AccountRepository,
        getAllSettingDataUseCase: GetAllSettingDataUseCase
    ): GetPostDataUseCase {
        return GetPostDataUseCase(accountRepository, getAllSettingDataUseCase)
    }
}