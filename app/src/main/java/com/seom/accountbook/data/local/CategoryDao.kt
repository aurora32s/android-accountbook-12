package com.seom.accountbook.data.local

import android.provider.BaseColumns
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity

object CategoryDao {
    const val TABLE_NAME = "CATEGORY"

    const val CREATE_TABLE = "" +
            "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${CategoryEntity.COLUMN_NAME_NAME} TEXT NOT NULL," +
            "${CategoryEntity.COLUMN_NAME_COLOR} INTEGER NOT NULL," +
            "${CategoryEntity.COLUMN_NAME_TYPE} INT NOT NULL)"
}