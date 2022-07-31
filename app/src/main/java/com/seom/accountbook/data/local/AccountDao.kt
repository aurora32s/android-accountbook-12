package com.seom.accountbook.data.local

import android.content.ContentValues
import android.provider.BaseColumns
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.di.provideAppDatabase

class AccountDao(
    val appDatabase: AppDatabase = provideAppDatabase()
) {
    companion object {
        private val TABLE_NAME = "ACCOUNT"
        val CREATE_TABLE = "" +
                "CREATE TABLE $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${AccountEntity.COLUMN_NAME_CONTENT} TEXT DEFAULT \"\"," +
                "${AccountEntity.COLUMN_NAME_YEAR} INTEGER NOT NULL," +
                "${AccountEntity.COLUMN_NAME_MONTH} INTEGER NOT NULL," +
                "${AccountEntity.COLUMN_NAME_DATE} INTEGER NOT NULL," +
                "${AccountEntity.COLUMN_NAME_COUNT} INTEGER NOT NULL," +
                "${AccountEntity.COLUMN_NAME_METHOD} INTEGER," +
                "${AccountEntity.COLUMN_NAME_CATEGORY} INTEGER DEFAULT -1," +
                "${AccountEntity.COLUMN_NAME_TYPE} INTEGER NOT NULL," +
                "FOREIGN KEY (${AccountEntity.COLUMN_NAME_METHOD}) REFERENCES ${MethodDao.TABLE_NAME} (${BaseColumns._ID})," +
                "FOREIGN KEY (${AccountEntity.COLUMN_NAME_CATEGORY}) REFERENCES ${CategoryDao.TABLE_NAME} (${BaseColumns._ID}))"
    }

    fun addAccount(account: AccountEntity): Long?{
        val db = appDatabase.writable
        val values = ContentValues().apply {
            put(AccountEntity.COLUMN_NAME_CONTENT, account.content)
            put(AccountEntity.COLUMN_NAME_YEAR, account.year)
            put(AccountEntity.COLUMN_NAME_MONTH, account.month)
            put(AccountEntity.COLUMN_NAME_DATE, account.date)
            put(AccountEntity.COLUMN_NAME_COUNT, account.count)
            put(AccountEntity.COLUMN_NAME_METHOD, account.methodId)
            put(AccountEntity.COLUMN_NAME_CATEGORY, account.categoryId)
            put(AccountEntity.COLUMN_NAME_TYPE, account.type)
        }

        return db?.insert(TABLE_NAME, null, values)
    }
}