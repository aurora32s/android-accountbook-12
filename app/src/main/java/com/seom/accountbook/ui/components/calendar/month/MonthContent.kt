package com.seom.accountbook.ui.components.calendar.month

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.calendar.day.DayState
import com.seom.accountbook.ui.components.calendar.week.WeekContent
import com.seom.accountbook.ui.components.calendar.week.getWeeks
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

internal const val DaysOfWeek = 7

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthContent(
    showAdjacentMonths: Boolean,
    currentMonth: YearMonth,
    daysOfWeek: List<DayOfWeek>,
    today: LocalDate,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState) -> Unit,
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            content = { weekHeader(daysOfWeek) })
        Spacer(modifier = Modifier.height(5.dp))
        monthContainer { paddingValues ->
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(paddingValues)
            ) {
                currentMonth.getWeeks(
                    includeAdjacentMonths = showAdjacentMonths,
                    firstDayOfTheWeek = daysOfWeek.first(),
                    today = today
                ).forEach { week ->
                    WeekContent(
                        week = week,
                        dayContent = dayContent
                    )
                }
            }
        }
    }
}