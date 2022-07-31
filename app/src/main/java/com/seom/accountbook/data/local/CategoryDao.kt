package com.seom.accountbook.data.local

import android.content.ContentValues
import android.provider.BaseColumns
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.di.provideAppDatabase

class CategoryDao(
    val appDatabase: AppDatabase = provideAppDatabase()
) {
    companion object {
        const val TABLE_NAME = "CATEGORY"

        const val CREATE_TABLE = "" +
                "CREATE TABLE $TABLE_NAME (" +
                "${CategoryEntity.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${CategoryEntity.COLUMN_NAME_NAME} TEXT NOT NULL," +
                "${CategoryEntity.COLUMN_NAME_COLOR} INTEGER NOT NULL," +
                "${CategoryEntity.COLUMN_NAME_TYPE} INT NOT NULL)"
    }

    fun addCategory(category: CategoryEntity): Long? {
        val db = appDatabase.writable
        val values = ContentValues().apply {
            put(CategoryEntity.COLUMN_NAME_NAME, category.name)
            put(CategoryEntity.COLUMN_NAME_COLOR, category.color)
            put(CategoryEntity.COLUMN_NAME_TYPE, category.type)
        }

        return db?.insert(TABLE_NAME, null, values)
    }

    fun updateCategory(category: CategoryEntity): Int {
        val db = appDatabase.writable
        val values = ContentValues().apply {
            put(CategoryEntity.COLUMN_NAME_NAME, category.name)
            put(CategoryEntity.COLUMN_NAME_COLOR, category.color)
        }

        val selection = "${CategoryEntity.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(category.id.toString())

        val result = db.update(
            TABLE_NAME,
            values,
            selection,
            selectionArgs
        )

        return result
    }

    fun getAllCategory(): List<CategoryEntity> {
        val db = appDatabase.readable
        val projection = arrayOf(
            CategoryEntity.COLUMN_NAME_ID,
            CategoryEntity.COLUMN_NAME_NAME,
            CategoryEntity.COLUMN_NAME_COLOR,
            CategoryEntity.COLUMN_NAME_TYPE
        )

        val cursor = db.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val categories = mutableListOf<CategoryEntity>()

        if (cursor.moveToFirst()) {
            do {
                categories.add(
                    CategoryEntity(
                        id = cursor.getLong(0),
                        name = cursor.getString(1),
                        color = cursor.getLong(2),
                        type = cursor.getInt(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return categories.toList()
    }

    fun getCategory(id: Long): CategoryEntity? {
        val db = appDatabase.readable

        val projection = arrayOf(
            CategoryEntity.COLUMN_NAME_ID,
            CategoryEntity.COLUMN_NAME_NAME,
            CategoryEntity.COLUMN_NAME_COLOR,
            CategoryEntity.COLUMN_NAME_TYPE
        )
        val selection = "${CategoryEntity.COLUMN_NAME_ID} = ?"
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
            CategoryEntity(
                id = cursor.getLong(0),
                name = cursor.getString(1),
                color = cursor.getLong(2),
                type = cursor.getInt(3)
            )
        } ?: kotlin.run {
            null
        }
    }
}