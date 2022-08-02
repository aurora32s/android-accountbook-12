package com.seom.accountbook.ui.components.selector

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun ColorSelector(
    colors: List<Long>,
    perLine: Int,
    selectedColor: Long,
    modifier: Modifier = Modifier,
    onSelectItem: (Long) -> Unit
) {
    var rowNum = colors.size / perLine
    if (colors.size % perLine != 0)
        rowNum++

    Column(modifier.padding(16.dp)) {
        (0 until rowNum).forEachIndexed { _, row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                (0 until min(perLine, colors.size)).forEachIndexed { _, column ->
                    val color = colors[row * perLine + column]
                    val paddingAnimation by animateDpAsState(targetValue = if (color == selectedColor) 0.dp else 4.dp)
                    Spacer(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(paddingAnimation)
                            .background(Color(color))
                            .clickable { onSelectItem(color) }
                    )
                }
            }
        }
    }
}