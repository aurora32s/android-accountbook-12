package com.seom.accountbook.data.local

import android.content.ContentValues
import android.provider.BaseColumns
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.di.provideAppDatabase

class MethodDao(
    private val appDatabase: AppDatabase = provideAppDatabase()
) {
    companion object {
        const val TABLE_NAME = "METHOD"

        const val CREATE_TABLE = "" +
                "CREATE TABLE $TABLE_NAME (" +
                "${MethodEntity.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${MethodEntity.COLUMN_NAME_NAME} TEXT NOT NULL)"
    }

    fun addMethod(method: MethodEntity): Long? {
        val db = appDatabase.writable
        val values = ContentValues().apply {
            put(MethodEntity.COLUMN_NAME_NAME, method.name)
        }

        return db?.insert(TABLE_NAME, null, values)
    }

    fun getAllMethods(): List<MethodEntity> {
        val db = appDatabase.readable
        val projectoin = arrayOf(
            MethodEntity.COLUMN_NAME_ID,
            MethodEntity.COLUMN_NAME_NAME
        )

        val cursor = db.query(
            TABLE_NAME,
            projectoin,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val methods = mutableListOf<MethodEntity>()
        if (cursor.moveToFirst()) {
            do {
                methods.add(
                    MethodEntity(
                        id = cursor.getLong(0),
                        name = cursor.getString(1)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return methods.toList()
    }

    fun getMethods(id: Long): MethodEntity? {
        val db = appDatabase.readable
        val projection = arrayOf(
            MethodEntity.COLUMN_NAME_ID,
            MethodEntity.COLUMN_NAME_NAME
        )

        val selection = "${MethodEntity.COLUMN_NAME_ID} = ?"
        val selectoinArgs = arrayOf(id.toString())

        val cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectoinArgs,
            null,
            null,
            null,
            "1"
        )
        return cursor?.use {
            cursor.moveToFirst()
            MethodEntity(
                id = cursor.getLong(0),
                name = cursor.getString(1)
            )
        } ?: kotlin.run {
            null
        }
    }

    fun updateMethod(method: MethodEntity): Int {
        val db = appDatabase.writable
        val values = ContentValues().apply {
            put(MethodEntity.COLUMN_NAME_NAME, method.name)
        }

        val selection = "${MethodEntity.COLUMN_NAME_ID} = ?"
        val selctionArgs = arrayOf(method.id.toString())

        val result = db.update(
            TABLE_NAME,
            values,
            selection,
            selctionArgs
        )

        return result
    }
}