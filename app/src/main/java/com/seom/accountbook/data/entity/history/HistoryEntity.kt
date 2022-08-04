package com.seom.accountbook.data.entity.history

import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.model.history.HistoryType

/**
 * 수입/지출 내역관련 데이터베이스에서 받아온 데이터
 */
data class HistoryEntity(
    val id: Long, // unique id
    val content: String, // 내용
    val year: Int,
    val month: Int,
    val date: Int,
    val money: Int, // 수입/지출 금액
    val method: String, // 결제방법
    val categoryName: String?, // 분류 이름
    val categoryColor: Long?, // 분류 색상
    val type: Int // 수입 or 지출
) {
    fun toModel() = HistoryModel(
        id = id,
        content = content,
        year = year,
        month = month,
        date = date,
        money = money,
        method = method,
        categoryName = categoryName ?: "미설정",
        categoryColor = if (categoryColor != null && categoryColor > 0) categoryColor
            else 0xFF242424,
        type = HistoryType.getHistoryType(type)
    )
}
