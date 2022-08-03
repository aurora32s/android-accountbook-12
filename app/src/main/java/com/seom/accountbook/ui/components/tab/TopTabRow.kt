package com.seom.accountbook.ui.components.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seom.accountbook.model.base.BaseType
import com.seom.accountbook.ui.theme.ColorPalette
import kotlin.math.pow

@Composable
fun <T : BaseType> TopTabRow(
    currentSelectedTopTab: Int,
    types: Array<T>,
    onTabSelected: (T) -> Unit,
    item: @Composable (T, Boolean) -> Unit,
    modifier: Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            types.forEach {
                TopTabItem(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(it) },
                    type = it,
                    selected = ((2.0).pow(it.type).toInt() and currentSelectedTopTab) > 0,
                    item = item
                )
            }
        }
    }
}

@Composable
private fun <T : BaseType> TopTabItem(
    modifier: Modifier = Modifier,
    type: T,
    selected: Boolean,
    item: @Composable (T, Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .background(
                if (selected) ColorPalette.Purple
                else ColorPalette.LightPurple
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item(type, selected)
    }
}