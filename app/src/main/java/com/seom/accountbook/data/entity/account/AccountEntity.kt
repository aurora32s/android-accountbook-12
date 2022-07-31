package com.seom.accountbook.data.entity.account

import android.provider.BaseColumns

data class AccountEntity(
    val id: Long? = null,
    val content: String, // 내용
    val year: Int, // 연도
    val month: Int, // 월
    val date: Int, // 일
    val count: Int, // 금액
    val methodId: Int? = null, // 결제수단 id
    val categoryId: Int? = null, // 카테고리 id
    val type: Int // 0: 수입, 1: 지출
) {
    companion object {
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_CONTENT = "CONTENT"
        const val COLUMN_NAME_YEAR = "YEAR"
        const val COLUMN_NAME_MONTH= "MONTH"
        const val COLUMN_NAME_DATE = "DATE"
        const val COLUMN_NAME_COUNT = "COUNT"
        const val COLUMN_NAME_METHOD = "METHOD"
        const val COLUMN_NAME_CATEGORY = "CATEGORY"
        const val COLUMN_NAME_TYPE= "TYPE"
    }
}
