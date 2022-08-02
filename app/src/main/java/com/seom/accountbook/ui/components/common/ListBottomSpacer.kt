package com.seom.accountbook.ui.components.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * 리스트 바닥 공간
 */
fun LazyListScope.BottomSpacer(
    height: Int
) {
    item {
        BaseDivider(color = ColorPalette.LightPurple)
        Spacer(modifier = Modifier.height(height.dp))
    }
}