package com.seom.accountbook.data.local

import android.provider.BaseColumns
import com.seom.accountbook.data.entity.method.MethodEntity

object MethodDao {
    const val TABLE_NAME = "METHOD"

    const val CREATE_TABLE = "" +
            "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${MethodEntity.COLUMN_NAME_NAME} TEXT NOT NULL)"
}