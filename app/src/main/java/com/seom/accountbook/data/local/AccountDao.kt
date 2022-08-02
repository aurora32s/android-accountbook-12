package com.seom.accountbook.data.local

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.core.database.getLongOrNull
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.di.provideAppDatabase
import com.seom.accountbook.model.graph.OutComeByCategory
import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.model.history.HistoryType


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

        return cursor?.use {
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

    fun getAllAccountByDate(year: Int, month: Int): List<HistoryModel> {
        val db = appDatabase.readable

        val query = "SELECT " +
                "A.${AccountEntity.COLUMN_NAME_ID}," +
                "A.${AccountEntity.COLUMN_NAME_CONTENT}," +
                "A.${AccountEntity.COLUMN_NAME_YEAR}," +
                "A.${AccountEntity.COLUMN_NAME_MONTH}," +
                "A.${AccountEntity.COLUMN_NAME_DATE}," +
                "A.${AccountEntity.COLUMN_NAME_COUNT}," +
                "M.${MethodEntity.COLUMN_NAME_NAME}," +
                "C.${CategoryEntity.COLUMN_NAME_NAME}," +
                "C.${CategoryEntity.COLUMN_NAME_COLOR}," +
                "A.${AccountEntity.COLUMN_NAME_TYPE} " +
                "FROM ${TABLE_NAME} A " +
                "LEFT JOIN ${MethodDao.TABLE_NAME} M " +
                "ON A.${AccountEntity.COLUMN_NAME_METHOD} = M.${MethodEntity.COLUMN_NAME_ID} " +
                "LEFT JOIN ${CategoryDao.TABLE_NAME} C " +
                "ON A.${AccountEntity.COLUMN_NAME_CATEGORY} = C.${CategoryEntity.COLUMN_NAME_ID} " +
                "WHERE A.${AccountEntity.COLUMN_NAME_YEAR} = $year AND A.${AccountEntity.COLUMN_NAME_MONTH} = $month"

        val cursor = db.rawQuery(query, null)

        val accounts = mutableListOf<HistoryModel>()
        if (cursor.moveToFirst()) {
            do {
                accounts.add(
                    HistoryModel(
                        id = cursor.getLong(0),
                        content = cursor.getString(1),
                        year = cursor.getInt(2),
                        month = cursor.getInt(3),
                        date = cursor.getInt(4),
                        money = cursor.getInt(5),
                        method = cursor.getString(6),
                        categoryName = cursor.getString(7),
                        categoryColor = cursor.getLong(8),
                        type = HistoryType.getHistoryType(cursor.getInt(9))
                    )
                )
            } while (cursor.moveToNext())
        }

        return accounts.toList()
    }

    fun removeAccount(accountItems: List<Long>): Int {
        val db = appDatabase.writable

        val whereClause =
            "${AccountEntity.COLUMN_NAME_ID} IN (${accountItems.map { "?" }.joinToString(",")})"
        val whereArgs = accountItems.map { it.toString() }.toTypedArray()

        val numRowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs)
        return numRowsDeleted
    }

    fun getAllAccountOnDate(year: Int, month: Int): List<CalendarEntity> {
        val db = appDatabase.writable
        val query = "SELECT " +
                "${AccountEntity.COLUMN_NAME_DATE}," +
                "SUM(${AccountEntity.COLUMN_NAME_COUNT})," +
                "${AccountEntity.COLUMN_NAME_TYPE} " +
                "FROM ${TABLE_NAME} " +
                "WHERE ${AccountEntity.COLUMN_NAME_YEAR} = $year " +
                "AND ${AccountEntity.COLUMN_NAME_MONTH} = $month " +
                "GROUP BY ${AccountEntity.COLUMN_NAME_TYPE}, ${AccountEntity.COLUMN_NAME_DATE}"

        val cursor = db.rawQuery(query, null)

        val accounts = mutableListOf<CalendarEntity>()
        if (cursor.moveToFirst()) {
            do {
                accounts.add(
                    CalendarEntity(
                        date = cursor.getInt(0),
                        count = cursor.getInt(1),
                        type = cursor.getInt(2)
                    )
                )
            } while (cursor.moveToNext())
        }

        return accounts.toList()
    }

    fun getDetailOutComeOnCategory(categoryId: Long, year: Int, month: Int): List<HistoryModel> {
        val db = appDatabase.readable

        val minYear = if (month < 6) year - 1 else year
        val minMonth = if (month < 6) 7 + month else month - 5

        val query = "SELECT " +
                "A.${AccountEntity.COLUMN_NAME_ID}," +
                "A.${AccountEntity.COLUMN_NAME_CONTENT}," +
                "A.${AccountEntity.COLUMN_NAME_YEAR}," +
                "A.${AccountEntity.COLUMN_NAME_MONTH}," +
                "A.${AccountEntity.COLUMN_NAME_DATE}," +
                "A.${AccountEntity.COLUMN_NAME_COUNT}," +
                "M.${MethodEntity.COLUMN_NAME_NAME}," +
                "C.${CategoryEntity.COLUMN_NAME_NAME}," +
                "C.${CategoryEntity.COLUMN_NAME_COLOR}," +
                "A.${AccountEntity.COLUMN_NAME_TYPE} " +
                "FROM ${TABLE_NAME} A " +
                "LEFT JOIN ${MethodDao.TABLE_NAME} M " +
                "ON A.${AccountEntity.COLUMN_NAME_METHOD} = M.${MethodEntity.COLUMN_NAME_ID} " +
                "LEFT JOIN ${CategoryDao.TABLE_NAME} C " +
                "ON A.${AccountEntity.COLUMN_NAME_CATEGORY} = C.${CategoryEntity.COLUMN_NAME_ID} " +
                "WHERE ${AccountEntity.COLUMN_NAME_YEAR} BETWEEN $minYear AND $year " +
                "AND (${AccountEntity.COLUMN_NAME_MONTH} >= $minMonth OR ${AccountEntity.COLUMN_NAME_MONTH} <= $month) " +
                "AND ${AccountEntity.COLUMN_NAME_CATEGORY} = $categoryId " +
                "ORDER BY ${AccountEntity.COLUMN_NAME_YEAR} DESC, ${AccountEntity.COLUMN_NAME_MONTH} DESC, ${AccountEntity.COLUMN_NAME_DATE} DESC"

        val cursor = db.rawQuery(query, null)

        val accounts = mutableListOf<HistoryModel>()
        if (cursor.moveToFirst()) {
            do {
                accounts.add(
                    HistoryModel(
                        id = cursor.getLong(0),
                        content = cursor.getString(1),
                        year = cursor.getInt(2),
                        month = cursor.getInt(3),
                        date = cursor.getInt(4),
                        money = cursor.getInt(5),
                        method = cursor.getString(6),
                        categoryName = cursor.getString(7),
                        categoryColor = cursor.getLong(8),
                        type = HistoryType.getHistoryType(cursor.getInt(9))
                    )
                )
            } while (cursor.moveToNext())
        }

        return accounts.toList()
    }

    fun getOutComeOnMonth(categoryId: Long, year: Int, month: Int): List<OutComeByMonth> {
        val db = appDatabase.readable

        val minYear = if (month < 6) year - 1 else year
        val minMonth = if (month < 6) 7 + month else month - 5

        val query = "" +
                "SELECT " +
                "SUM(${AccountEntity.COLUMN_NAME_COUNT})," +
                "${AccountEntity.COLUMN_NAME_MONTH} " +
                "FROM $TABLE_NAME " +
                "WHERE ${AccountEntity.COLUMN_NAME_YEAR} BETWEEN $minYear AND $year " +
                "AND (${AccountEntity.COLUMN_NAME_MONTH} >= $minMonth OR ${AccountEntity.COLUMN_NAME_MONTH} <= $month) " +
                "AND ${AccountEntity.COLUMN_NAME_CATEGORY} = $categoryId " +
                "GROUP BY ${AccountEntity.COLUMN_NAME_MONTH} " +
                "ORDER BY ${AccountEntity.COLUMN_NAME_YEAR}, ${AccountEntity.COLUMN_NAME_MONTH}"


        val cursor = db.rawQuery(query, null)

        val accounts = mutableListOf<OutComeByMonth>()
        if (cursor.moveToFirst()) {
            do {
                accounts.add(
                    OutComeByMonth(
                        count = cursor.getLong(0),
                        name = cursor.getInt(1).toString()
                    )
                )
            } while (cursor.moveToNext())
        }

        return accounts.toList()
    }

    fun getOutComeOnCategory(year: Int, month: Int): List<OutComeByCategory> {
        val db = appDatabase.readable
        val query = "" +
                "SELECT " +
                "C.${CategoryEntity.COLUMN_NAME_ID}," +
                "C.${CategoryEntity.COLUMN_NAME_NAME}," +
                "C.${CategoryEntity.COLUMN_NAME_COLOR}," +
                "SUM(${AccountEntity.COLUMN_NAME_COUNT}) AS S " +
                "FROM ${TABLE_NAME} A " +
                "LEFT JOIN ${CategoryDao.TABLE_NAME} C " +
                "ON A.${AccountEntity.COLUMN_NAME_CATEGORY} = C.${CategoryEntity.COLUMN_NAME_ID} " +
                "WHERE A.${AccountEntity.COLUMN_NAME_TYPE} = ${HistoryType.OUTCOME.type} " +
                "AND A.${AccountEntity.COLUMN_NAME_YEAR} = $year " +
                "AND A.${AccountEntity.COLUMN_NAME_MONTH} = $month " +
                "GROUP BY A.${AccountEntity.COLUMN_NAME_CATEGORY} " +
                "ORDER BY S DESC"

        val cursor = db.rawQuery(query, null)

        val accounts = mutableListOf<OutComeByCategory>()
        if (cursor.moveToFirst()) {
            do {
                accounts.add(
                    OutComeByCategory(
                        id = cursor.getLong(0),
                        name = cursor.getString(1) ?: "UnKnown",
                        color = cursor.getLong(2) ?: 0xFFECECEC,
                        count = cursor.getLong(3)
                    )
                )
            } while (cursor.moveToNext())
        }

        return accounts.toList()
    }
}