package com.seom.accountbook.ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import com.seom.accountbook.ui.components.image.IconImage
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun CustomFloatingActionButton(
    onClickBtn: () -> Unit,
    @DrawableRes
    icon: Int
) {
    FloatingActionButton(
        onClick = onClickBtn,
        backgroundColor = ColorPalette.Yellow
    ) {
        IconImage(icon)
    }
}