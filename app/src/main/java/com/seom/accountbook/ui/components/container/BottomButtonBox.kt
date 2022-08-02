package com.seom.accountbook.ui.components.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * 하단에 버튼이 있는 Box Layout
 */
@Composable
fun BottomButtonBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonText: String,
    buttonColor: Color,
    disabledColor: Color = buttonColor,
    onClickBtn: () -> Unit,
    body: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        body()
        Button(
            onClick = onClickBtn,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor,
                disabledBackgroundColor = disabledColor
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            CustomText(
                text = buttonText,
                style = MaterialTheme.typography.subtitle1,
                bold = true,
                color = ColorPalette.White,
                paddingVertical = 8,
                modifier = Modifier
                    .background(Color.Transparent)
            )
        }
    }
}