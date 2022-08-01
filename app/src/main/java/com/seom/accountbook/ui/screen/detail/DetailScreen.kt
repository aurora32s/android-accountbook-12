package com.seom.accountbook.ui.screen.detail

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seom.accountbook.R
import com.seom.accountbook.mock.histories
import com.seom.accountbook.model.BaseCount
import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.screen.history.HistoryListHeader
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney

val mockData = listOf(
    OutComeByMonth(
        id = 0,
        count = 509637,
        color = 0xFF524D90,
        name = "2"
    ),
    OutComeByMonth(
        id = 1,
        count = 563283,
        color = 0xFFA79FCB,
        name = "3"
    ),
    OutComeByMonth(
        id = 2,
        count = 590106,
        color = 0xFFE75B3F,
        name = "4"
    ),
    OutComeByMonth(
        id = 3,
        count = 643752,
        color = 0xFFF5B853,
        name = "5"
    ),
    OutComeByMonth(
        id = 4,
        count = 568647,
        color = 0xFF4EAABA,
        name = "6"
    ),
    OutComeByMonth(
        id = 5,
        count = 536460,
        color = 0xFFA79FCB,
        name = "7"
    )
)

@Composable
fun DetailScreen(
    categoryId: String? = null,
    onBackButtonPressed: () -> Unit
) {
    val categoryName = "생활"

    Scaffold(
        topBar = {
            OneButtonAppBar(title = categoryName) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.clickable { onBackButtonPressed() })
            }
        }
    ) {

        Column {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )
            LinearGraph(
                data = mockData,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = ColorPalette.White,
                lineColor = Brush.verticalGradient(
                    colors = listOf(
                        ColorPalette.LightPurple,
                        ColorPalette.Purple
                    )
                ),
                primaryTextColor = ColorPalette.Purple,
                secondaryTextColor = ColorPalette.LightPurple
            )
            Divider(
                color = ColorPalette.LightPurple,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutComeList(
                historyGroupedByDate = histories
            )
        }
    }
}

val blockNum = 6
val linearGraphPadding = 80f

@Composable
fun LinearGraph(
    data: List<BaseCount>,
    modifier: Modifier = Modifier,
    backgroundColor: Color, // 배경 색
    lineColor: Brush, // 선 색
    primaryTextColor: Color, // 강조 글자 색
    secondaryTextColor: Color // 일반 글자 색
) {
    Column(
        modifier = Modifier
            .height(130.dp)
            .background(backgroundColor)
            .padding(7.dp)
    ) {
        Canvas(
            modifier
                .weight(1f)
                .padding(top = 40.dp, bottom = 40.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val minValue = data.minOf { it.count }
            val maxValue = data.maxOf { it.count }
            val diff = (maxValue - minValue)

            val blockWidth = canvasWidth / (blockNum * 2)
            val pointX = data.mapIndexed { index, row -> (blockWidth) * (index * 2 + 1) }
            val pointY =
                data.map { canvasHeight - (it.count - minValue) / (diff / canvasHeight) }

            for (index in 1 until data.size) {
                val start = Offset(x = pointX[index], y = pointY[index])
                val end = Offset(x = pointX[index - 1], y = pointY[index - 1])
                drawLine(
                    start = start,
                    end = end,
                    brush = lineColor,
                    strokeWidth = (2.0).toFloat().dp.toPx()
                )
            }

            data.forEachIndexed { index, row ->
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        row.count.toMoney(),
                        pointX[index],
                        pointY[index] - (8.sp).toPx(),
                        Paint().apply {
                            textSize = (10.sp).toPx()
                            color =
                                (if (index == data.size - 1) primaryTextColor else secondaryTextColor).toArgb()
                            typeface =
                                Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            data.forEachIndexed { index, row ->
                Text(
                    text = row.name,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight(700),
                        color = if (index == data.size - 1) primaryTextColor else secondaryTextColor
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OutComeList(
    // TODO Key 를 String 으로 두는게 맞을까...
    historyGroupedByDate: Map<String, List<HistoryModel>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        historyGroupedByDate.forEach { (date, histories) ->
            stickyHeader {
                HistoryListHeader(
                    date = date,
                    income = histories.filter { it.type == HistoryType.INCOME }
                        .sumOf { it.money },
                    outCome = histories.filter { it.type == HistoryType.OUTCOME }
                        .sumOf { it.money }
                )
            }
            items(items = histories) { history ->
                OutComeListItem(
                    history = history
                )
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
fun OutComeListItem(
    history: HistoryModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
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
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (history.categoryName != null) {
                        Text(
                            text = history.categoryName,
                            modifier = Modifier
                                .widthIn(56.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(history.categoryColor!!))
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
                    }
                    history.method?.let {
                        Text(
                            text = history.method,
                            style = MaterialTheme.typography.subtitle1,
                            color = ColorPalette.Purple,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(top = 4.dp)
                        )
                    }
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
    }
}