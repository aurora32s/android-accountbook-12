package com.seom.accountbook.model.history

import androidx.annotation.StringRes
import androidx.compose.ui.layout.IntrinsicMeasurable
import com.seom.accountbook.R
import com.seom.accountbook.model.category.incomeColor
import com.seom.accountbook.model.category.outcomeColor

enum class HistoryType(
    val type: Int, // 0: Income, 1: Outcome
    @StringRes
    val title: Int,
    val colorList: List<Long>
) {
    INCOME(0, R.string.type_history_income, incomeColor),
    OUTCOME(1, R.string.type_history_outcome, outcomeColor);

    companion object {
        fun getHistoryType(typeId: Int) = values().find { it.type == typeId } ?: INCOME
    }
}