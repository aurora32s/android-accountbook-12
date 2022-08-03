package com.seom.accountbook.ui.components.datesheet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.seom.accountbook.ui.components.CustomBottomSheet
import com.seom.accountbook.ui.components.numberpicker.OutlinedNumberPicker
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.NOW
import com.seom.accountbook.util.ext.dayOfWeekText
import com.seom.accountbook.util.ext.getLastDate
import java.time.LocalDate

/**
 * 연도/월/일/요일 포함하는 data bottom sheet
 */
@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FullDateBottomSheet(
    sheetState: ModalBottomSheetState,
    onClickCloseBtn: () -> Unit,
    onChangeDate: (LocalDate) -> Unit, // 날짜 변경
    modifier: Modifier = Modifier,
    body: @Composable () -> Unit
) {
    CustomBottomSheet(
        sheetState = sheetState,
        sheetContent = {
            FullDateSheetContent(
                modifier = modifier,
                onClickCloseBtn = onClickCloseBtn,
                onChangeDate = onChangeDate
            )
        },
        body = body
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun FullDateSheetContent(
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now(),
    onClickCloseBtn: () -> Unit,
    onChangeDate: (LocalDate) -> Unit
) {
    val selectedYear = remember { mutableStateOf(currentDate.year) }
    val selectedMonth = remember { mutableStateOf(currentDate.month.value) }
    val selectedDate = remember { mutableStateOf(currentDate.dayOfMonth) }
    val lastDate = LocalDate.of(selectedYear.value, selectedMonth.value, 1).getLastDate()

    BasicDateBottomSheet(
        modifier = modifier,
        onClickCloseBtn = onClickCloseBtn,
        onClickConfirmBtn = {
            onChangeDate(
                LocalDate.of(
                    selectedYear.value,
                    selectedMonth.value,
                    selectedDate.value
                )
            )
        }
    ) {
        OutlinedNumberPicker(
            state = selectedYear,
            minValue = NOW.year - 39,
            maxValue = NOW.year,
            spacer = 8,
            text = "년"
        )
        OutlinedNumberPicker(
            state = selectedMonth,
            minValue = 1,
            maxValue = 12,
            spacer = 8,
            text = "월"
        )
        OutlinedNumberPicker(
            state = selectedDate,
            minValue = 1,
            maxValue = lastDate,
            spacer = 8,
            text = "일"
        )
        Text(
            text = "${
                LocalDate.of(
                    selectedYear.value,
                    selectedMonth.value,
                    if (selectedDate.value > lastDate)
                        lastDate
                    else selectedDate.value
                ).dayOfWeekText()
            }요일",
            style = MaterialTheme.typography.caption,
            color = ColorPalette.LightPurple
        )
    }
}