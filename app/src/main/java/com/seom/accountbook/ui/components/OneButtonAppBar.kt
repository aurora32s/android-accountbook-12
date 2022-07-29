package com.seom.accountbook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * 왼쪽에 버튼이 하나만 있는 AppBar
 */
@Composable
fun OneButtonAppBar(
    title: String,
    leftIcon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        leftIcon()
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = ColorPalette.Purple
        )
        Spacer(modifier = Modifier.width(24.dp))
    }
}