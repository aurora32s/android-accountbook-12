package com.seom.accountbook.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun Chip(
    text: String,
    color: Color
) {
    CustomText(
        text = text,
        style = MaterialTheme.typography.caption,
        bold = true,
        color = ColorPalette.White,
        align = TextAlign.Center,
        modifier = Modifier
            .widthIn(56.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(color)
            .padding(
                start = 8.dp,
                top = 4.dp,
                bottom = 4.dp,
                end = 8.dp
            )
    )
}