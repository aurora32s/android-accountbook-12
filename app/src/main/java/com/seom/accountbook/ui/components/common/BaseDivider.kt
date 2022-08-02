package com.seom.accountbook.ui.components.common

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * Lazy Column 에 사용되는 Divider
 */
@Composable
fun BaseDivider(
    color: Color
) {
    Divider(
        color = color,
        thickness = 1.dp
    )
}