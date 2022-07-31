package com.seom.accountbook.data.local

import android.content.ContentValues
import android.provider.BaseColumns
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
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
                "${AccountEntity.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
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

    fun addAccount(account: AccountEntity): Long? {
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

    fun updateAccount(account: AccountEntity): Int {
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

        val selection = "${AccountEntity.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(account.id.toString())

        val result = db.update(
            TABLE_NAME,
            values,
            selection,
            selectionArgs
        )

        return result
    }

    fun getAccount(id: Long): AccountEntity? {
        val db = appDatabase.readable

        val projection = arrayOf(
            AccountEntity.COLUMN_NAME_ID,
            AccountEntity.COLUMN_NAME_CONTENT,
            AccountEntity.COLUMN_NAME_YEAR,
            AccountEntity.COLUMN_NAME_MONTH,
            AccountEntity.COLUMN_NAME_DATE,
            AccountEntity.COLUMN_NAME_COUNT,
            AccountEntity.COLUMN_NAME_METHOD,
            AccountEntity.COLUMN_NAME_CATEGORY,
            AccountEntity.COLUMN_NAME_TYPE
        )
        val selection = "${AccountEntity.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null,
            "1"
        )

        return cursor?.let {
            cursor.moveToFirst()
            AccountEntity(
                id = cursor.getLong(0),
                content = cursor.getString(1),
                year = cursor.getInt(2),
                month = cursor.getInt(3),
                date = cursor.getInt(4),
                count = cursor.getInt(5),
                methodId = cursor.getLongOrNull(6),
                categoryId = cursor.getLongOrNull(7),
                type = cursor.getInt(8)
            )
        } ?: kotlin.run {
            null
        }
    }
}