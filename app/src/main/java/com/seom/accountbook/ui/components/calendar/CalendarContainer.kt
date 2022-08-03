package com.seom.accountbook.ui.components.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.seom.accountbook.ui.components.calendar.day.DayState
import com.seom.accountbook.ui.components.calendar.month.DaysOfWeek
import com.seom.accountbook.ui.components.calendar.month.MonthContent
import com.seom.accountbook.ui.components.calendar.month.MonthState
import com.seom.accountbook.ui.components.calendar.week.DefaultWeekHeader
import com.seom.accountbook.ui.components.calendar.week.rotateRight
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@Stable
class CalendarState(
    val monthState: MonthState
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarContainer(
    calendarState: CalendarState,
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
    today: LocalDate = LocalDate.now(),
    showAdjacentMonths: Boolean = true,
    dayContent: @Composable BoxScope.(DayState) -> Unit,
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(dayOfWeek = it) },
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
        Box { content(PaddingValues()) }
    }
) {
    val dayOfWeek = remember(firstDayOfWeek) {
        DayOfWeek.values().rotateRight(DaysOfWeek - firstDayOfWeek.ordinal)
    }

    Column(modifier = modifier) {
        MonthContent(
            showAdjacentMonths = showAdjacentMonths,
            currentMonth = calendarState.monthState.currentMonth,
            daysOfWeek = dayOfWeek,
            today = today,
            dayContent = dayContent,
            weekHeader = weekHeader,
            monthContainer = monthContainer
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun rememberCalendarState(
    initialMonth: YearMonth = YearMonth.now(),
    monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
        MonthState(initialMonth = initialMonth)
    },
): CalendarState = remember { CalendarState(monthState) }