package com.seom.accountbook.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * Custom Input Field
 */
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    name: String,
    value: String,
    textColor: Color,
    onlyNumber: Boolean = false,
    onValueChanged: (String) -> Unit
) {
    InputField(
        modifier = modifier,
        name = name,
        textColor = textColor
    ) {
        Box {
            BasicTextField(
                value = value,
                onValueChange = onValueChanged,
                textStyle = MaterialTheme.typography.subtitle1.copy(
                    color = textColor,
                    fontWeight = FontWeight(700)
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = if (onlyNumber) KeyboardOptions(keyboardType = KeyboardType.Number)
                else KeyboardOptions.Default
            )
            if (value.isBlank()) {
                CustomText(
                    text = "입력하세요",
                    style = MaterialTheme.typography.subtitle1,
                    bold = true,
                    color = ColorPalette.LightPurple
                )
            }
        }
    }
}