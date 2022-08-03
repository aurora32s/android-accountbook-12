package com.seom.accountbook.ui.components.calendar.week

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seom.accountbook.ui.components.calendar.day.DayState

@Composable
internal fun WeekContent(
    week: Week,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = if (week.isFirstWeekOfTheMonth) Arrangement.End else Arrangement.Start
    ) {
        week.days.forEachIndexed { index, day ->
            Box(
                modifier = Modifier.fillMaxWidth(1f / (7 - index))
            ) {
                dayContent(
                    DayState(day)
                )
            }
        }
    }
}