package com.seom.accountbook.data.local

import android.content.ContentValues
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.model.history.HistoryType
import javax.inject.Inject

class MethodDao(
    private val appDatabase: AppDatabase
) {
    companion object {
        const val TABLE_NAME = "METHOD"

        const val CREATE_TABLE = "" +
                "CREATE TABLE $TABLE_NAME (" +
                "${MethodEntity.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${MethodEntity.COLUMN_NAME_NAME} TEXT NOT NULL," +
                "${MethodEntity.COLUMN_NAME_TYPE} INTEGER NOT NULL)"

        val INIT_DATA = listOf(
            MethodEntity(name = "현대카드", type = HistoryType.OUTCOME.type),
            MethodEntity(name = "카카오뱅크 체크카드", type = HistoryType.OUTCOME.type)
        )
    }

    fun addMethod(method: MethodEntity): Long? {
        val db = appDatabase.writable
        if (checkMethodName(method.name, method.type)) {
            return -1
        }
        val values = ContentValues().apply {
            put(MethodEntity.COLUMN_NAME_NAME, method.name)
            put(MethodEntity.COLUMN_NAME_TYPE, method.type)
        }

        return db?.insert(TABLE_NAME, null, values)
    }

    private fun checkMethodName(name: String, type: Int): Boolean {
        val db = appDatabase.readable

        val projection = arrayOf(
            MethodEntity.COLUMN_NAME_ID
        )
        val selection =
            "${MethodEntity.COLUMN_NAME_NAME} = ? AND ${MethodEntity.COLUMN_NAME_TYPE} = ?"
        val selectionArgs = arrayOf(name, type.toString())

        val cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null, null, null,
            "1"
        )

        return cursor?.use {
            cursor.moveToFirst()
        } ?: kotlin.run {
            false
        }
    }

    fun getAllMethods(): List<MethodEntity> {
        val db = appDatabase.readable
        val projectoin = arrayOf(
            MethodEntity.COLUMN_NAME_ID,
            MethodEntity.COLUMN_NAME_NAME,
            MethodEntity.COLUMN_NAME_TYPE
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
                        name = cursor.getString(1),
                        type = cursor.getInt(2)
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
            MethodEntity.COLUMN_NAME_NAME,
            MethodEntity.COLUMN_NAME_TYPE
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
                name = cursor.getString(1),
                type = cursor.getInt(2)
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