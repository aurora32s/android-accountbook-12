package com.seom.accountbook.ui.screen.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.screen.calendar.CalendarContainer
import com.seom.accountbook.ui.screen.calendar.RowData
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GraphScreen() {
    DateAppBar(
        onDateChange = {

        },
        children = {
            Column {
                Divider(
                    color = ColorPalette.Purple,
                    thickness = 1.dp
                )
                TopRow(totalCount = 834640)
                Divider(
                    color = ColorPalette.LightPurple,
                    thickness = 1.dp
                )
            }
        }
    )
}

@Composable
fun TopRow(
    totalCount: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 9.dp, end = 16.dp, bottom = 9.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "이번 달 총 지출 금액",
            style = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple)
        )
        Text(
            text = totalCount.toMoney(),
            style = MaterialTheme.typography.caption.copy(color = ColorPalette.Red)
        )
    }
}