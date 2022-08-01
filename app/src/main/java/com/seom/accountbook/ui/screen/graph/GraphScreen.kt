package com.seom.accountbook.ui.screen.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seom.accountbook.Detail
import com.seom.accountbook.model.BaseCount
import com.seom.accountbook.model.graph.OutComeByCategory
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.screen.calendar.CalendarContainer
import com.seom.accountbook.ui.screen.calendar.RowData
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney
import java.time.LocalDate

val mockData = listOf(
    OutComeByCategory(id = 0, name = "생활", color = 0xFF4A6CC3, count = 536460),
    OutComeByCategory(
        id = 1,
        name = "의료/건강",
        color = 0xFF6ED5EB,
        count = 125300
    ),
    OutComeByCategory(
        id = 2,
        name = "쇼핑/뷰티",
        color = 0xFF4CB8B8,
        count = 56000
    ),
    OutComeByCategory(id = 3, name = "교통", color = 0xFF94D3CC, count = 45340),
    OutComeByCategory(id = 4, name = "식비", color = 0xFF4CA1DE, count = 40540),
    OutComeByCategory(
        id = 5,
        name = "문화/여가",
        color = 0xFFD092E2,
        count = 20800
    ),
    OutComeByCategory(id = 6, name = "미분류", color = 0xFF817DCE, count = 10200)
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GraphScreen(
    viewModel: GraphViewModel,
    onPushNavigate: (String, String) -> Unit
) {
    var accounts by remember { mutableStateOf<List<OutComeByCategory>>(emptyList()) }

    val observer = viewModel.graphUiState.collectAsState()
    when (val result = observer.value) {
        GraphUiState.UnInitialized -> {
            val current = LocalDate.now()

            val year = current.year
            val month = current.month.value
            viewModel.fetchData(year, month)
        }
        GraphUiState.Loading -> {}
        is GraphUiState.SuccessFetch -> {
            accounts = result.accounts
            println(accounts)
        }
        is GraphUiState.Error -> {}
    }

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
                Spacer(modifier = Modifier.height(24.dp))
                CircleGraphByRate(
                    date = mockData,
                    totalCount = 834640,
                    modifier = Modifier
                        .height(254.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                CategoryList(
                    data = mockData,
                    totalCount = 834640,
                    onItemClick = { onPushNavigate(Detail.route, it.toString()) })
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

private const val DividerLengthInDegrees = 1.8f

private enum class AnimatedCircleProgress { START, END }

@Composable
fun CircleGraphByRate(
    date: List<BaseCount>,
    totalCount: Long,
    modifier: Modifier = Modifier
) {
    val currentState = remember {
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply { targetState = AnimatedCircleProgress.END }
    }
    val stroke = with(LocalDensity.current) { Stroke(45.dp.toPx()) }
    val transition = updateTransition(currentState)
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = LinearOutSlowInEasing
            )
        }
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            360f
        }
    }

    Canvas(modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        var startAngle = -90f
        date.forEachIndexed { index, rate ->
            val sweep = (rate.count / totalCount.toFloat()) * angleOffset
            drawArc(
                color = Color(rate.color),
                startAngle = startAngle + DividerLengthInDegrees / 2,
                sweepAngle = sweep - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = stroke
            )
            startAngle += sweep
        }
    }
}

@Composable
fun CategoryList(
    data: List<OutComeByCategory>,
    totalCount: Long,
    onItemClick: (Long) -> Unit
) {
    LazyColumn(
    ) {
        items(items = data) { row ->
            CategoryOutComeItem(
                data = row,
                totalCount = totalCount,
                modifier = Modifier.clickable { onItemClick(row.id) })
        }
        item {
            Divider(
                color = ColorPalette.LightPurple,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CategoryOutComeItem(
    data: OutComeByCategory,
    totalCount: Long,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight(700),
                        color = ColorPalette.White
                    ),
                    modifier = Modifier
                        .widthIn(56.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(data.color))
                        .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = data.count.toMoney(),
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight(500),
                        color = ColorPalette.Purple
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = "${((data.count / totalCount.toFloat()) * 100).toInt().toString()}%",
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple)
            )
        }
        Divider(color = ColorPalette.Purple40, thickness = 1.dp)
    }
}