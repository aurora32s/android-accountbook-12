package com.seom.accountbook.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onValueChanged: (String) -> Unit
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
                Box {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChanged,
                        textStyle = MaterialTheme.typography.subtitle1.copy(color = textColor),
                        modifier = Modifier.fillMaxWidth()
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
        Divider(
            color = ColorPalette.Purple40,
            thickness = 1.dp
        )
    }
}