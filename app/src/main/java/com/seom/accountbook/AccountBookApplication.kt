package com.seom.accountbook

import android.app.Application
import com.seom.accountbook.di.provideAppDatabase

class AccountBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val db = provideAppDatabase(context = applicationContext)
    }
}