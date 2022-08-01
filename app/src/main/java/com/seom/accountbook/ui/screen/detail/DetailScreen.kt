package com.seom.accountbook.ui.screen.detail

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.seom.accountbook.ui.screen.graph.GraphUiState
import com.seom.accountbook.ui.screen.history.HistoryListHeader
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.fullFormat
import com.seom.accountbook.util.ext.toMoney
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    year: Int,
    month: Int,
    categoryId: String,
    viewModel: DetailViewModel,
    onBackButtonPressed: () -> Unit
) {
    val observer = viewModel.detailUiState.collectAsState()
    when (val result = observer.value) {
        DetailUiState.UnInitialized -> {
            viewModel.fetchData(categoryId = categoryId.toLong(), year, month)
        }
        DetailUiState.Loading -> {}
        is DetailUiState.SuccessFetch -> {
            DetailBody(
                title = result.accounts[0].categoryName ?: "UnKnown",
                accounts = result.accounts,
                month = month,
                outComeByMonth = result.outComeByMonth,
                onBackButtonPressed = onBackButtonPressed
            )
        }
        is DetailUiState.Error -> {}
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailBody(
    title: String,
    month: Int,
    accounts: List<HistoryModel>,
    outComeByMonth: List<OutComeByMonth>,
    onBackButtonPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            OneButtonAppBar(title = title) {
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
                data = outComeByMonth,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = ColorPalette.White,
                lineColor = Brush.verticalGradient(
                    colors = listOf(
                        ColorPalette.LightPurple,
                        ColorPalette.Purple
                    )
                ),
                primaryTextColor = ColorPalette.Purple,
                secondaryTextColor = ColorPalette.LightPurple,
                keys = (5 downTo 0).map { index ->
                    (
                        if (month - index > 0) month - index else 12 + month - index
                    ).toString()
                }
            )
            Divider(
                color = ColorPalette.LightPurple,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutComeList(
                historyGroupedByDate = accounts.groupBy { LocalDate.of(it.year, it.month, it.date) }
            )
        }
    }
}

val blockNum = 6
val linearGraphPadding = 80f

@Composable
fun LinearGraph(
    data: List<OutComeByMonth>,
    keys: List<String>,
    modifier: Modifier = Modifier,
    backgroundColor: Color, // 배경 색
    lineColor: Brush, // 선 색
    primaryTextColor: Color, // 강조 글자 색
    secondaryTextColor: Color // 일반 글자 색
) {
    val usedData = keys.map { name -> data.find { it.name == name }?.count ?: 0  }
    Column(
        modifier = Modifier
            .height(130.dp)
            .background(backgroundColor)
            .padding(7.dp)
    ) {
        if (data.isNotEmpty()) {
            Canvas(
                modifier
                    .weight(1f)
                    .padding(top = 20.dp, bottom = 20.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val minValue = usedData.minOf { it }
                val maxValue = usedData.maxOf { it }
                val diff = (maxValue - minValue)

                val blockWidth = canvasWidth / (blockNum * 2)
                val pointX = (0..5).map { index -> (blockWidth) * (index * 2 + 1) }
                val pointY = usedData.map { canvasHeight - (it - minValue) / (diff / canvasHeight) }

                for (index in 1 until usedData.size) {
                    val start = Offset(x = pointX[index], y = pointY[index])
                    val end = Offset(x = pointX[index - 1], y = pointY[index - 1])
                    drawLine(
                        start = start,
                        end = end,
                        brush = lineColor,
                        strokeWidth = (2.0).toFloat().dp.toPx()
                    )
                }

                keys.forEachIndexed { index, name  ->
                    drawIntoCanvas {
                        val count = data.find { it.name == name }?.count ?: 0
                        it.nativeCanvas.drawText(
                            count.toString(),
                            pointX[index],
                            pointY[index] - (8.sp).toPx(),
                            Paint().apply {
                                textSize = (10.sp).toPx()
                                color =
                                    (if (index == keys.size - 1) primaryTextColor else secondaryTextColor).toArgb()
                                typeface =
                                    Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            keys.forEachIndexed { index, row ->
                Text(
                    text = row,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight(700),
                        color = if (index == keys.size - 1) primaryTextColor else secondaryTextColor
                    )
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OutComeList(
    // TODO Key 를 String 으로 두는게 맞을까...
    historyGroupedByDate: Map<LocalDate, List<HistoryModel>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        historyGroupedByDate.forEach { (date, histories) ->
            stickyHeader {
                HistoryListHeader(
                    date = date.fullFormat(),
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