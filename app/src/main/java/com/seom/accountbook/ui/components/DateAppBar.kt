package com.seom.accountbook.ui.components

import android.widget.NumberPicker
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
import com.seom.accountbook.R
import com.seom.accountbook.model.common.Date
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.format
import kotlinx.coroutines.launch
import java.util.*

/**
 * 화면 상단에서 일자 변경이 가능한 app bar
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DateAppBar(
    currentDate: Date, // 현재 선택된 날짜
    onDateChange: (Int, Int) -> Unit, // 선택된 날짜 변경 이벤트
    children: @Composable () -> Unit
) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            DatePickerBottomSheet(
                currentDate = currentDate,
                onCloseBottomSheet = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                onClickConfirmBtn = { year, month ->
                    onDateChange(year, month)
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppBar(currentDate = currentDate) {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }
            }
        ) {
            children()
        }
    }
}

@Composable
fun AppBar(
    currentDate: Date, // 현재 선택된 날짜
    onClickDate: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Transparent
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(painter = painterResource(id = R.drawable.ic_left), contentDescription = null)
            Text(
                text = currentDate.format(),
                style = MaterialTheme.typography.body1,
                color = ColorPalette.Purple,
                modifier = Modifier.clickable { onClickDate() }
            )
            Image(painter = painterResource(id = R.drawable.ic_right), contentDescription = null)
        }
    }
}

@Composable
fun DatePickerBottomSheet(
    currentDate: Date,
    onCloseBottomSheet: () -> Unit,
    onClickConfirmBtn: (Int, Int) -> Unit
) {
    var year = currentDate.year
    var month = currentDate.month

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
                state = remember { mutableStateOf(year) },
                range = 0..year,
                onStateChanged = { year = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "년",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.LightPurple
            )
            Spacer(modifier = Modifier.width(8.dp))
            NumberPicker(
                state = remember { mutableStateOf(month) },
                range = 1..12,
                onStateChanged = { month = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "월",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.LightPurple
            )
        }
        Button(
            onClick = { onClickConfirmBtn(year, month) },
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