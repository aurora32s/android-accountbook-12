package com.seom.accountbook.ui.screen.calendar.day

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DefaultDate(
    account: Map<Int, List<CalendarEntity>>,
    state: DayState,
    modifier: Modifier = Modifier,
    currentDayColor: Color = ColorPalette.White
) {
    val date = state.date

    val come = account[date.dayOfMonth]
    val income = come?.find { it.type == HistoryType.INCOME.type }?.count ?: 0
    val outcome = come?.find { it.type == HistoryType.OUTCOME.type }?.count ?: 0

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .heightIn(60.dp),
        border = BorderStroke(1.dp, ColorPalette.Purple40),
        backgroundColor = if (state.isCurrentDay) currentDayColor else ColorPalette.OffWhite,
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier.padding(4.dp)
        ) {
            if (state.isFromCurrentMonth) {
                Column(
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = income.toMoney(),
                        fontWeight = FontWeight(500),
                        color = ColorPalette.Green,
                        fontSize = 8.sp
                    )
                    Text(
                        text = (-1 * outcome).toMoney(),
                        fontWeight = FontWeight(500),
                        color = ColorPalette.Red,
                        fontSize = 8.sp
                    )
                    Text(
                        text = (income - outcome).toMoney(),
                        fontWeight = FontWeight(500),
                        color = ColorPalette.Purple,
                        fontSize = 8.sp
                    )
                }
            }
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = if (state.isFromCurrentMonth) ColorPalette.Purple else ColorPalette.Purple40,
                    fontWeight = FontWeight(700)
                ),
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}