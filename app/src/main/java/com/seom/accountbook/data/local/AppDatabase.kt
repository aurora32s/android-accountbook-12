package com.seom.accountbook.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity

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

        // 기본 데이터 추가
        CategoryDao.INIT_DATA.forEach {
            val values = ContentValues().apply {
                put(CategoryEntity.COLUMN_NAME_NAME, it.name)
                put(CategoryEntity.COLUMN_NAME_COLOR, it.color)
                put(CategoryEntity.COLUMN_NAME_TYPE, it.type)
            }
            db.insertOrThrow(CategoryDao.TABLE_NAME, null, values)
        }
        MethodDao.INIT_DATA.forEach {
            val values = ContentValues().apply {
                put(MethodEntity.COLUMN_NAME_NAME, it.name)
                put(MethodEntity.COLUMN_NAME_TYPE, it.type)
            }
            db.insertOrThrow(MethodDao.TABLE_NAME, null, values)
        }
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