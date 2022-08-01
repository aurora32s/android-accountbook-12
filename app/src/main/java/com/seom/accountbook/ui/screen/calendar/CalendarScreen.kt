package com.seom.accountbook.ui.screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.screen.calendar.day.DayState
import com.seom.accountbook.ui.screen.calendar.day.DefaultDate
import com.seom.accountbook.ui.screen.calendar.month.DaysOfWeek
import com.seom.accountbook.ui.screen.calendar.month.MonthContent
import com.seom.accountbook.ui.screen.calendar.month.MonthState
import com.seom.accountbook.ui.screen.calendar.week.DefaultWeekHeader
import com.seom.accountbook.ui.screen.calendar.week.rotateRight
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney
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
    viewModel: CalendarViewModel,
    onPushNavigate: (String, String) -> Unit
) {
    var histories by remember { mutableStateOf<List<CalendarEntity>>(emptyList()) }

    val observer = viewModel.categoryUiState.collectAsState()
    when (val result = observer.value) {
        CalendarUiState.UnInitialized -> {
            val current = LocalDate.now()

            val year = current.year
            val month = current.month.value
            viewModel.fetchData(year, month)
        }
        CalendarUiState.Loading -> {}
        is CalendarUiState.SuccessFetch -> {
            histories = result.accounts
            println(histories)
        }
        is CalendarUiState.Error -> {}
    }

    val calendarState = rememberCalendarState()
    val calendarDate = histories.groupBy { it.date }
    val income = histories.filter { it.type == HistoryType.INCOME.type }.sumOf { it.count }
    val outcome = histories.filter { it.type == HistoryType.OUTCOME.type }.sumOf { it.count }

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
                Spacer(modifier = Modifier.height(20.dp))
                CalendarContainer(
                    calendarState = calendarState,
                    dayContent = {
                        DefaultDate(
                            account = calendarDate,
                            state = it
                        )
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                RowData(title = "수입", data = income, dateColor = ColorPalette.Green)
                RowData(title = "지출", data = -1 * outcome, dateColor = ColorPalette.Red)
                RowData(title = "총합", data = income - outcome, dateColor = ColorPalette.Purple)
                Divider(
                    color = ColorPalette.LightPurple,
                    thickness = 1.dp
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

@Composable
fun RowData(
    title: String,
    data: Int,
    dateColor: Color
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 8.dp,
                    bottom = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight(500),
                    color = ColorPalette.Purple
                )
            )
            Text(
                text = data.toMoney(),
                style = MaterialTheme.typography.caption.copy(
                    color = dateColor
                )
            )
        }
        Divider(
            color = ColorPalette.Purple40,
            thickness = 1.dp
        )
    }
}