package com.seom.accountbook.ui.screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.screen.calendar.day.DayState
import com.seom.accountbook.ui.screen.calendar.day.DefaultDate
import com.seom.accountbook.ui.screen.calendar.month.DaysOfWeek
import com.seom.accountbook.ui.screen.calendar.month.MonthContent
import com.seom.accountbook.ui.screen.calendar.month.MonthState
import com.seom.accountbook.ui.screen.calendar.week.DefaultWeekHeader
import com.seom.accountbook.ui.screen.calendar.week.rotateRight
import com.seom.accountbook.ui.theme.ColorPalette
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@Stable
public class CalendarState(
    public val monthState: MonthState
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    onPushNavigate: (String, String) -> Unit
) {
    val calendarState = rememberCalendarState()
    DateAppBar(
        onDateChange = {
            // TODO 변경된 날짜에 맞는 데이터 요청
            calendarState.monthState.currentMonth = YearMonth.from(it)
        },
        children = {
            Column {
                Divider(
                    color = ColorPalette.Purple,
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.height(5.dp))
                CalendarContainer(
                    calendarState = calendarState
                )
            }
        })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarContainer(
    calendarState: CalendarState,
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
    today: LocalDate = LocalDate.now(),
    showAdjacentMonths: Boolean = true,
    dayContent: @Composable BoxScope.(DayState) -> Unit = { DefaultDate(state = it) },
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
public fun rememberCalendarState(
    initialMonth: YearMonth = YearMonth.now(),
    monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
        MonthState(initialMonth = initialMonth)
    },
): CalendarState = remember { CalendarState(monthState) }
