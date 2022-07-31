package com.seom.accountbook.di

import android.content.Context
import com.seom.accountbook.data.local.AccountDao
import com.seom.accountbook.data.local.AppDatabase
import com.seom.accountbook.data.local.CategoryDao
import com.seom.accountbook.data.local.MethodDao

private var appDatabase: AppDatabase? = null
fun provideAppDatabase(context: Context? = null): AppDatabase {
    if (context != null) appDatabase = AppDatabase.build(context)
    return appDatabase!!
}

private var accountDao: AccountDao? = null
fun provideAccountDao(): AccountDao {
    if (accountDao == null) accountDao = AccountDao()
    return accountDao!!
}

private var categoryDao: CategoryDao? = null
fun provideCategoryDao(): CategoryDao {
    if (categoryDao == null) categoryDao = CategoryDao()
    return categoryDao!!
}

private var methodDao: MethodDao? = null
fun provideMethodDao(): MethodDao {
    if (methodDao == null) methodDao = MethodDao()
    return methodDao!!
}