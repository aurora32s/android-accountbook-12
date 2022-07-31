package com.seom.accountbook.di

import android.content.Context
import com.seom.accountbook.data.local.AccountDao
import com.seom.accountbook.data.local.AppDatabase

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