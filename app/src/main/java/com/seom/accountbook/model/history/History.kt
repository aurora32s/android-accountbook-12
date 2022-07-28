package com.seom.accountbook.model.history

data class History(
    val id: Int, // unique id
    val content: String, // 내용
    val money: Int, // 수입/지출 금액
    val method: String, // 결제방법
    val categoryName: String, // 분류 이름
    val categoryColor: Long, // 분류 색상
    val type: HistoryType // 수입 or 지출
)
