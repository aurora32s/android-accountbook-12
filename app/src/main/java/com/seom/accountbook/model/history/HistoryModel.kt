package com.seom.accountbook.model.history

data class HistoryModel(
    val id: Long, // unique id
    val content: String, // 내용
    val year: Int,
    val month: Int,
    val date: Int,
    val money: Int, // 수입/지출 금액
    val method: String, // 결제방법 or 입금계좌
    val categoryName: String, // 분류 이름
    val categoryColor: Long, // 분류 색상
    val type: HistoryType // 수입 or 지출
)
