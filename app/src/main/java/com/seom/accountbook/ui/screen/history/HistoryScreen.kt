package com.seom.accountbook.ui.screen.history

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.mock.histories
import com.seom.accountbook.model.history.History
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
            color = ColorPalette.Purple,
            thickness = 2.dp
        )
        Column {
            // 수입 / 지출 tab
            HistoryTopTab(
                currentSelectedTab = currentSelectedTab,
                onTabSelected = { currentSelectedTab = it },
                modifier = Modifier
                    .padding(16.dp)
            )
            // 수입 / 지출 리스트
            HistoryList(
                historyGroupedByDate = histories,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun HistoryTopTab(
    currentSelectedTab: HistoryType,
    onTabSelected: (HistoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent
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
            style = MaterialTheme.typography.subtitle1,
            color = ColorPalette.White,
            modifier = Modifier.padding(
                start = 5.dp
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryList(
    // TODO Key 를 String 으로 두는게 맞을까...
    historyGroupedByDate: Map<String, List<History>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        historyGroupedByDate.forEach { (date, histories) ->
            item {
                HistoryListHeader(date = date, income = 1000, outCome = 2000)
            }
            items(items = histories) { history ->
                HistoryListItem(history = history)
            }
            item {
                Divider(
                    color = ColorPalette.LightPurple,
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun HistoryListHeader(
    date: String,
    income: Int,
    outCome: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorPalette.OffWhite)
            .padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 8.dp,
                end = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.body2,
            color = ColorPalette.LightPurple
        )
        Text(
            text = "수입 $income 지출 $outCome",
            style = MaterialTheme.typography.subtitle1,
            color = ColorPalette.LightPurple
        )
    }
}

@Composable
fun HistoryListItem(
    history: History
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
    ) {
        Divider(
            color = ColorPalette.Purple40,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = history.categoryName,
                modifier = Modifier
                    .widthIn(56.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(history.categoryColor))
                    .padding(
                        start = 8.dp,
                        top = 4.dp,
                        bottom = 4.dp,
                        end = 8.dp
                    ),
                style = MaterialTheme.typography.subtitle2,
                color = ColorPalette.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = history.method,
                style = MaterialTheme.typography.subtitle1,
                color = ColorPalette.Purple,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = history.content,
                style = MaterialTheme.typography.caption,
                color = ColorPalette.Purple
            )
            Text(
                text = "${
                    if (history.type == HistoryType.OUTCOME) -1 * history.money
                    else history.money
                } 원",
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight(700),
                color = if (history.type == HistoryType.INCOME) ColorPalette.Green else ColorPalette.Red
            )
        }

    }
}