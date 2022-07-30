package com.seom.accountbook.di

import android.content.Context
import com.seom.accountbook.data.local.AppDatabase

private var appDatabase: AppDatabase? = null
fun provideAppDatabase(context: Context): AppDatabase {
    if (appDatabase == null) appDatabase = AppDatabase.build(context)
    return appDatabase!!
}