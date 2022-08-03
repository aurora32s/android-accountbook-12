package com.seom.accountbook.ui.components.graph

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seom.accountbook.model.base.BaseLinearGraphModel

@Composable
fun LinearGraph(
    data: List<BaseLinearGraphModel>,
    keys: List<String>,
    modifier: Modifier = Modifier,
    backgroundColor: Color, // 배경 색
    lineColor: Brush, // 선 색
    primaryTextColor: Color, // 강조 글자 색
    secondaryTextColor: Color, // 일반 글자 색
    blockNum: Int = 6
) {
    val usedData = keys.map { name -> data.find { it.name == name }?.count ?: 0 }
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

                keys.forEachIndexed { index, name ->
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