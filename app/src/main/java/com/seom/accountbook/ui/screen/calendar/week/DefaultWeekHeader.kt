package com.seom.accountbook.ui.screen.calendar.week

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.seom.accountbook.ui.theme.ColorPalette
import java.time.DayOfWeek
import java.time.format.TextStyle.SHORT
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DefaultWeekHeader(
    dayOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        dayOfWeek.forEach { date ->
            Text(
                text = date.getDisplayName(SHORT, Locale.getDefault()),
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple),
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center
            )
        }
    }
}

internal fun <T> Array<T>.rotateRight(n: Int): List<T> = takeLast(n) + dropLast(n)