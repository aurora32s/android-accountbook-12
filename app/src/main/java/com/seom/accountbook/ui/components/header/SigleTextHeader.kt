package com.seom.accountbook.ui.components.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * 왼쪽에 글씨 하나만 있는 Header
 */
@Composable
fun SingleTextHeader(
    title: String
) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 8.dp,
            top = 8.dp
        )
    ) {
        CustomText(
            text = title,
            style = MaterialTheme.typography.body2,
            bold = false,
            color = ColorPalette.LightPurple
        )
        Divider(
            color = ColorPalette.Purple40,
            thickness = 1.dp
        )
    }
}