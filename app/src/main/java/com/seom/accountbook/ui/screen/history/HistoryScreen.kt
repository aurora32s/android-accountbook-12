package com.seom.accountbook.ui.screen.history

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.theme.ColorPalette
import java.util.*

@Preview(
    name = "HistoryScreen"
)
@Composable
fun HistoryScreen() {
    var currentSelectedTab by remember { mutableStateOf(HistoryType.INCOME) }
    var currentDate by remember { mutableStateOf(Date()) }

    Scaffold(
        topBar = {
            // 상단 날짜 tab
            DateAppBar(
                currentDate = currentDate,
                onDateChange = { currentDate = it }
            )
        }
    ) {
        Divider(
            color = ColorPalette.Purple
        )
        // 수입 / 지출 tab
        HistoryTopTab(
            currentSelectedTab = currentSelectedTab,
            onTabSelected = { currentSelectedTab = it },
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
fun HistoryTopTab(
    currentSelectedTab: HistoryType,
    onTabSelected: (HistoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            // 수입 tab
            HistoryType.values().forEach {
                HistoryTypeItem(
                    type = it,
                    selected = it == currentSelectedTab,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onTabSelected(it)
                        }
                )
            }
        }
    }
}

@Composable
fun HistoryTypeItem(
    type: HistoryType,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                if (selected) ColorPalette.Purple
                else ColorPalette.LightPurple
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (selected) ColorPalette.White else Color.Transparent)
                .border(
                    width = 1.dp,
                    color = ColorPalette.Purple,
                    shape = RoundedCornerShape(2.dp)
                )
        ) {
            if (selected) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = ColorPalette.Purple)
                )
            }
        }

        Text(
            text = stringResource(id = type.title),
            style = MaterialTheme.typography.body2,
            color = ColorPalette.White,
            modifier = Modifier.padding(
                start = 5.dp
            )
        )
    }
}
