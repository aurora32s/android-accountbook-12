package com.seom.accountbook.ui.screen.calendar.week

import androidx.compose.runtime.Immutable
import com.seom.accountbook.ui.screen.calendar.day.Day

@Immutable
internal class Week(
    val isFirstWeekOfTheMonth: Boolean = false,
    val days: List<Day>
)