package com.seom.accountbook.ui.components.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * text 만 있는 app bar
 */
@Composable
fun NoneButtonAppBar(
    title: String
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomText(
                text = title,
                style = MaterialTheme.typography.body1,
                bold = false,
                color = ColorPalette.Purple
            )
        }

        BaseDivider(color = ColorPalette.Purple)
    }
}