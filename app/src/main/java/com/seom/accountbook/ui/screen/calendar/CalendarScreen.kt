package com.seom.accountbook.ui.screen.calendar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.seom.accountbook.ui.components.DateAppBar

@Composable
fun CalendarScreen(
    onPushNavigate: (String, String) -> Unit
) {
    DateAppBar(
        onDateChange = {
            // TODO 변경된 날짜에 맞는 데이터 요청
        },
        children = {
            Text(text = "hello world")
        })
}