package com.seom.accountbook.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Text
 */
@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    bold: Boolean = false,
    color: Color,
    align: TextAlign = TextAlign.Start,
    paddingVertical: Int = 0,
    paddingHorizontal: Int = 0
) {
    Text(
        text = text,
        style = style,
        fontWeight = FontWeight(if (bold) FONT_WEIGHT_BOLD else FONT_WEIGHT_LIGHT),
        color = color,
        textAlign = align,
        modifier = modifier
            .padding(
                start = paddingHorizontal.dp,
                top = paddingVertical.dp,
                end = paddingHorizontal.dp,
                bottom = paddingVertical.dp
            )
    )
}

const val FONT_WEIGHT_BOLD = 700
const val FONT_WEIGHT_LIGHT = 500