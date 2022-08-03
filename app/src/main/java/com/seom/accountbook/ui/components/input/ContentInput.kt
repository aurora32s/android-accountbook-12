package com.seom.accountbook.ui.components.input

import androidx.compose.runtime.Composable
import com.seom.accountbook.ui.components.text.CustomTextField
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun ContentInput(
    content: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        name = "내용",
        value = content,
        textColor = ColorPalette.Purple,
        onValueChanged = onValueChange
    )
}