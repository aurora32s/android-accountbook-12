package com.seom.accountbook.data.local

import android.content.ContentValues
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.di.provideAppDatabase
import com.seom.accountbook.model.history.HistoryType

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
                "${CategoryEntity.COLUMN_NAME_TYPE} INTEGER NOT NULL)"

        val INIT_DATA = listOf(
            CategoryEntity(name = "교통", color = 0xFF94D3CC, type = HistoryType.OUTCOME.type),
            CategoryEntity(name = "문화/여가", color = 0xFFD092E2, type = HistoryType.OUTCOME.type),
            CategoryEntity(name = "미분류", color = 0xFF817DCE, type = HistoryType.OUTCOME.type),
            CategoryEntity(name = "생활", color = 0xFF4A6CC3, type = HistoryType.OUTCOME.type),
            CategoryEntity(name = "쇼핑/뷰티", color = 0xFF4CB8B8, type = HistoryType.OUTCOME.type),
            CategoryEntity(name = "식비", color = 0xFF4CA1DE, type = HistoryType.OUTCOME.type),
            CategoryEntity(name = "의료/건강", color = 0xFF6ED5EB, type = HistoryType.OUTCOME.type),
            CategoryEntity(name = "월급", color = 0xFF9BD182, type = HistoryType.INCOME.type),
            CategoryEntity(name = "용돈", color = 0xFFEDCF65, type = HistoryType.INCOME.type),
            CategoryEntity(name = "기타", color = 0xFFE29C4D, type = HistoryType.INCOME.type)
        )
    }

    fun addCategory(category: CategoryEntity): Long? {
        val db = appDatabase.writable
        if (checkCategoryName(category.name, category.type)) {
            return -1
        }
        val values = ContentValues().apply {
            put(CategoryEntity.COLUMN_NAME_NAME, category.name)
            put(CategoryEntity.COLUMN_NAME_COLOR, category.color)
            put(CategoryEntity.COLUMN_NAME_TYPE, category.type)
        }

        return db?.insertOrThrow(TABLE_NAME, null, values)
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

    private fun checkCategoryName(name: String, type: Int): Boolean {
        val db = appDatabase.readable

        val projection = arrayOf(
            CategoryEntity.COLUMN_NAME_ID
        )
        val selection = "${CategoryEntity.COLUMN_NAME_NAME} = ? AND ${CategoryEntity.COLUMN_NAME_TYPE} = ?"
        val selectionArgs = arrayOf(name, type.toString())

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

        return cursor?.use {
            cursor.moveToFirst()
        } ?: kotlin.run {
            false
        }
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