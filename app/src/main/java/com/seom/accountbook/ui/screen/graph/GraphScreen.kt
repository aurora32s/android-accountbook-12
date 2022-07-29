package com.seom.accountbook.ui.screen.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.screen.calendar.CalendarContainer
import com.seom.accountbook.ui.screen.calendar.RowData
import com.seom.accountbook.ui.theme.ColorPalette

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
            }
        }
    )
}