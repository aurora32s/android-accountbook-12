package com.seom.accountbook.ui.components.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

/**
 * 색 지정이 가능한 Image
 */
@Composable
fun IconImage(
    @DrawableRes
    icon: Int,
    color: Color
) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        colorFilter = ColorFilter.tint(color)
    )
}