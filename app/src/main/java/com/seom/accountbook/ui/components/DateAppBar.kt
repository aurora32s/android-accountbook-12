package com.seom.accountbook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.format
import com.seom.accountbook.util.ext.toYearAndMonth
import kotlinx.coroutines.launch
import java.util.*

/**
 * 화면 상단에서 일자 변경이 가능한 app bar
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DateAppBar(
    currentDate: Date, // 현재 선택된 날짜
    onDateChange: (Date) -> Unit, // 선택된 날짜 변경 이벤트
    children: @Composable () -> Unit
) {

    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            Row() {
                Text(text = "hello world")
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Scaffold(
            topBar = {
                AppBar(currentDate = currentDate) {
                    coroutineScope.launch {
                        bottomSheetState.bottomSheetState.expand()
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
                text = currentDate.toYearAndMonth(),
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

) {
    
}