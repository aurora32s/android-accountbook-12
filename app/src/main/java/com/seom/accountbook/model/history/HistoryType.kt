package com.seom.accountbook.model.history

import androidx.annotation.StringRes
import com.seom.accountbook.R

enum class HistoryType(
    val type: Int, // 0: Income, 1: Outcome
    @StringRes
    val title: Int
) {
    INCOME(0, R.string.type_history_income),
    OUTCOME(1, R.string.type_history_outcome);

    companion object {
        fun getHistoryType(typeId: Int) = values().find { it.type == typeId } ?: INCOME
    }
}