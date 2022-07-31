package com.seom.accountbook.ui.components.numberpicker

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.NumberPicker

@Composable
fun OutlinedNumberPicker(
    state: MutableState<Int>,
    minValue: Int,
    maxValue: Int,
    spacer: Int,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    textColor: Color = MaterialTheme.colors.secondary
) {
    NumberPicker(
        state = state,
        range = minValue..maxValue
    )
    Spacer(modifier = Modifier.width(spacer.dp))
    Text(
        text = text,
        style = textStyle,
        color = textColor
    )
    Spacer(modifier = Modifier.width(spacer.dp))
}
