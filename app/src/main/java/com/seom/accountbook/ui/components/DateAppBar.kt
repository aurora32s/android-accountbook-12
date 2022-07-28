package com.seom.accountbook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.format
import com.seom.accountbook.util.ext.toYearAndMonth
import java.util.*

/**
 * 화면 상단에서 일자 변경이 가능한 app bar
 */
@Composable
fun DateAppBar(
    currentDate: Date, // 현재 선택된 날짜
    onDateChange: (Date) -> Unit, // 선택된 날짜 변경 이벤트
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
                color = ColorPalette.Purple
            )
            Image(painter = painterResource(id = R.drawable.ic_right), contentDescription = null)
        }
    }
}