package com.seom.accountbook.model.history

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.seom.accountbook.R
import com.seom.accountbook.model.base.BaseType
import com.seom.accountbook.model.category.incomeColor
import com.seom.accountbook.model.category.outcomeColor
import com.seom.accountbook.ui.theme.ColorPalette

enum class HistoryType(
    override val type: Int, // 0: Income, 1: Outcome
    @StringRes
    override val title: Int,
    val color: Color,
    val colorList: List<Long>
): BaseType{
    INCOME(0, R.string.type_history_income, ColorPalette.Green, incomeColor),
    OUTCOME(1, R.string.type_history_outcome, ColorPalette.Red, outcomeColor);

    companion object {
        fun getHistoryType(typeId: Int) = values().find { it.type == typeId } ?: INCOME

        fun HistoryType.getCountOnType(count: Long): Long {
            if (this == INCOME) return count
            else return -count
        }
    }
}