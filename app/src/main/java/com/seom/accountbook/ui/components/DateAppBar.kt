package com.seom.accountbook.ui.components

import android.os.Build
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.seom.accountbook.R
import com.seom.accountbook.model.common.Date
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.format
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.util.*

/**
 * 화면 상단에서 일자 변경이 가능한 app bar
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DateAppBar(
    year: Int,
    month: Int,
    onDateChange: (LocalDate) -> Unit, // 선택된 날짜 변경 이벤트
    children: @Composable () -> Unit,
    header: (@Composable () -> Unit)? = null,
    actionButton: @Composable () -> Unit = {}
) {
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val maxYear = LocalDate.now().year
    val maxMonth = LocalDate.now().month.value
    val minYear = year - 30

    val date = remember { mutableStateOf(Date(year, month)) }

    val selectYear = remember { mutableStateOf(year) }
    val selectMonth = remember { mutableStateOf(month) }

    val onChangeDate = { newDate: Date ->
        date.value = newDate
        selectYear.value = newDate.year
        selectMonth.value = newDate.month

        onDateChange(LocalDate.of(newDate.year, newDate.month, 1))
    }

    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            DatePickerBottomSheet(
                year = selectYear,
                month = selectMonth,
                maxYear = maxYear,
                minYear = minYear,
                onCloseBottomSheet = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                onClickConfirmBtn = {
                    if (selectMonth.value > maxMonth) {
                        onChangeDate(date.value)
                    } else {
                        onChangeDate(
                            Date(selectYear.value, selectMonth.value)
                        )
                    }
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        },
        body = {
            Scaffold(
                topBar = header ?: {
                    AppBar(
                        date = date,
                        onClickDate = {
                            coroutineScope.launch {
                                bottomSheetState.show()
                            }
                        },
                        onPrevDate = {
                            val value = date.value
                            val newDate = when {
                                value.month == 1 && value.year == minYear -> Date(
                                    value.year,
                                    value.month
                                )
                                value.month == 1 -> Date(value.year - 1, 12)
                                else -> Date(value.year, value.month - 1)
                            }

                            onChangeDate(newDate)
                        },
                        onPostDate = {
                            val value = date.value
                            val newDate = when {
                                value.month == maxMonth && value.year == maxYear -> Date(
                                    value.year,
                                    value.month
                                )
                                value.month == 12 -> Date(value.year + 1, 1)
                                else -> Date(value.year, value.month + 1)
                            }

                            onChangeDate(newDate)
                        }
                    )
                },
                floatingActionButton = actionButton
            ) {
                children()
            }
        }
    )
}

@Composable
fun AppBar(
    date: MutableState<Date>,
    onClickDate: () -> Unit,
    onPrevDate: () -> Unit,
    onPostDate: () -> Unit
) {
    Column(
        modifier = Modifier.background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_left),
                contentDescription = null,
                modifier = Modifier.clickable { onPrevDate() })
            Text(
                text = date.value.format(),
                style = MaterialTheme.typography.body1,
                color = ColorPalette.Purple,
                modifier = Modifier.clickable { onClickDate() }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_right),
                contentDescription = null,
                modifier = Modifier.clickable { onPostDate() })
        }
        BaseDivider(color = ColorPalette.Purple)
    }
}

@Composable
fun DatePickerBottomSheet(
    year: MutableState<Int>,
    month: MutableState<Int>,
    maxYear: Int,
    minYear: Int,
    onCloseBottomSheet: () -> Unit,
    onClickConfirmBtn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 20.dp,
                bottom = 16.dp,
                end = 16.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "날짜 선택",
                style = MaterialTheme.typography.body2,
                color = ColorPalette.Purple,
                fontWeight = FontWeight(700)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorPalette.Purple),
                modifier = Modifier.clickable {
                    onCloseBottomSheet()
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberPicker(
                state = year,
                range = minYear..maxYear
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "년",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.LightPurple
            )
            Spacer(modifier = Modifier.width(8.dp))
            NumberPicker(
                state = month,
                range = 1..12
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "월",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.LightPurple
            )
        }
        Button(
            onClick = { onClickConfirmBtn() },
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = ColorPalette.Yellow)
        ) {
            Text(
                text = "조회",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.White
            )
        }
    }
}