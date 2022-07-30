package com.seom.accountbook.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val writable
        get() = getWritableDatabase()
    val readable
        get() = readableDatabase

    override fun onCreate(db: SQLiteDatabase) {
        // TODO TABLE 생성
        db.execSQL(MethodDao.CREATE_TABLE)
        db.execSQL(CategoryDao.CREATE_TABLE)
        db.execSQL(AccountDao.CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // TODO 업데이트 시 데이터 이전 되도록 수정해보기
    }

    companion object {
        private const val DATABASE_NAME = "accountbook.db"
        private const val DATABASE_VERSION = 1

        fun build(context: Context) = AppDatabase(context.applicationContext)
    }
}