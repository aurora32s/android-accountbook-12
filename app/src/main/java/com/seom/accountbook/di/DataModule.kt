package com.seom.accountbook.di

import android.content.Context
import com.seom.accountbook.data.local.AccountDao
import com.seom.accountbook.data.local.AppDatabase
import com.seom.accountbook.data.local.CategoryDao
import com.seom.accountbook.data.local.MethodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AccountDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CategoryDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MethodDataSource

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @AccountDataSource
    @Provides
    fun provideAccountDataSource(
        database: AppDatabase
    ): AccountDao = AccountDao(database)

    @Singleton
    @CategoryDataSource
    @Provides
    fun provideCategoryDataSource(
        database: AppDatabase
    ): CategoryDao = CategoryDao(database)

    @Singleton
    @MethodDataSource
    @Provides
    fun provideMethodDataSource(
        database: AppDatabase
    ): MethodDao = MethodDao(database)
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.build(context)
    }
}