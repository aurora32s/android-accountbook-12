package com.seom.accountbook.ui.screen.detail

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seom.accountbook.R
import com.seom.accountbook.model.BaseCount
import com.seom.accountbook.model.category.Category
import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.model.method.Method
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.screen.post.PostBody
import com.seom.accountbook.ui.screen.post.PostTopTab
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney
import kotlinx.coroutines.launch

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