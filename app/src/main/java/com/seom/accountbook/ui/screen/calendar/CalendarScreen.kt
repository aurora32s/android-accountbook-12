package com.seom.accountbook.ui.screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seom.accountbook.AccountViewModel
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.components.calendar.CalendarContainer
import com.seom.accountbook.ui.components.calendar.CalendarState
import com.seom.accountbook.ui.components.calendar.rememberCalendarState
import com.seom.accountbook.ui.components.calendar.day.DefaultDate
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.components.common.SideItemRow
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    mainViewModel: AccountViewModel,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val year = mainViewModel.year.collectAsState()
    val month = mainViewModel.month.collectAsState()
    val calendarState = rememberCalendarState()

    LaunchedEffect(key1 = year.value, key2 = month.value) {
        viewModel.fetchData(year.value, month.value)
        calendarState.monthState.currentMonth =
            YearMonth.from(LocalDate.of(year.value, month.value, 1))
    }

    val histories = viewModel.histories.collectAsState()
    DateAppBar(
        year = year.value,
        month = month.value,
        onDateChange = {
            mainViewModel.setDate(it.year, it.month.value)
        },
        children = {
            CalendarBody(
                calendarState = calendarState,
                histories = histories.value
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarBody(
    calendarState: CalendarState,
    histories: List<CalendarEntity>
) {
    val income = histories.filter { it.type == HistoryType.INCOME.type }.sumOf { it.count }
    val outcome = histories.filter { it.type == HistoryType.OUTCOME.type }.sumOf { it.count }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        CalendarContainer(
            calendarState = calendarState,
            dayContent = {
                DefaultDate(
                    account = histories.groupBy { it.date },
                    state = it
                )
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        RowData(title = "수입", data = income, dateColor = ColorPalette.Green)
        RowData(title = "지출", data = -1 * outcome, dateColor = ColorPalette.Red)
        RowData(title = "총합", data = income - outcome, dateColor = ColorPalette.Purple)
        BaseDivider(color = ColorPalette.LightPurple)
    }
}

@Composable
fun RowData(
    title: String,
    data: Long,
    dateColor: Color
) {
    Column {
        SideItemRow(
            left = {
                CustomText(
                    text = title,
                    style = MaterialTheme.typography.subtitle1,
                    color = ColorPalette.Purple
                )
            },
            right = {
                CustomText(
                    text = data.toMoney(),
                    style = MaterialTheme.typography.subtitle1,
                    color = dateColor
                )
            },
            modifier = Modifier.padding(
                start = 15.dp,
                top = 8.dp,
                end = 15.dp,
                bottom = 8.dp
            )
        )
        BaseDivider(color = ColorPalette.Purple40)
    }
}