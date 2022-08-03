package com.seom.accountbook.ui.components.text

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    name: String,
    textColor: Color,
    input: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
            CustomText(
                text = name,
                style = MaterialTheme.typography.subtitle1,
                bold = false,
                color = textColor,
                modifier = Modifier.weight(2f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                modifier = Modifier.weight(8f),
                color = Color.Transparent
            ) {
                input()
            }
        }
        BaseDivider(color = ColorPalette.Purple40)
    }
}