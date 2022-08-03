package com.seom.accountbook.di

import com.seom.accountbook.data.local.AccountDao
import com.seom.accountbook.data.local.CategoryDao
import com.seom.accountbook.data.local.MethodDao
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.CategoryRepository
import com.seom.accountbook.data.repository.MethodRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.data.repository.impl.CategoryRepositoryImpl
import com.seom.accountbook.data.repository.impl.MethodRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAccountRepository(
        @AccountDataSource accountDao: AccountDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AccountRepository {
        return AccountRepositoryImpl(accountDao, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        @CategoryDataSource categoryDao: CategoryDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideMethodRepository(
        @MethodDataSource methodDao: MethodDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MethodRepository {
        return MethodRepositoryImpl(methodDao, ioDispatcher)
    }

}