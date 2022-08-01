package com.seom.accountbook.mock

import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.model.history.HistoryType

val histories = hashMapOf<String, List<HistoryModel>>(
    "7월7일 금요일" to mutableListOf<HistoryModel>().apply {
        (0..20).forEach {
            this.add(
                HistoryModel(
                    id = it.toLong(),
                    year = 2022,
                    month = 7,
                    date = 6,
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
    "7월6일 목요일" to mutableListOf<HistoryModel>().apply {
        (0..20).forEach {
            this.add(
                HistoryModel(
                    id = (it + 20).toLong(),
                    year = 2022,
                    month = 7,
                    date = 6,
                    content = "content $it",
                    money = it * 1000,
                    method = "현대카드",
                    categoryName = "미분류",
                    categoryColor = 0xFFECECEC,
                    type = HistoryType.OUTCOME
                )
            )
        }
    }.toList()
)