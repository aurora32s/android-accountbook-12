package com.seom.accountbook.data.local

import android.provider.BaseColumns
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity

object AccountDao {
    private const val TABLE_NAME = "ACCOUNT"

    const val CREATE_TABLE = "" +
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