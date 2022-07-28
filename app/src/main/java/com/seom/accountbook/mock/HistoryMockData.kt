package com.seom.accountbook.mock

import com.seom.accountbook.model.history.History
import com.seom.accountbook.model.history.HistoryType

val histories = hashMapOf<String, List<History>>(
    "7월7일 금요일" to mutableListOf<History>().apply {
        (0..20).forEach {
            this.add(
                History(
                    id = it,
                    content = "content $it",
                    money = it * 1000,
                    method = "현대카드",
                    categoryName = "미분류",
                    categoryColor = 0xFFECECEC,
                    type = HistoryType.INCOME
                )
            )
        }
    }.toList(),
    "7월6일 목요일" to mutableListOf<History>().apply {
        (0..20).forEach {
            this.add(
                History(
                    id = it + 20,
                    content = "content $it",
                    money = it * 1000,
                    method = "현대카드",
                    categoryName = "미분류",
                    categoryColor = 0xFFECECEC,
                    type = HistoryType.getHistoryType(it % 2)
                )
            )
        }
    }.toList()
)