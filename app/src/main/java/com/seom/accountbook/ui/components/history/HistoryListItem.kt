package com.seom.accountbook.ui.components.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.components.image.IconImage
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryType.Companion.getCountOnType
import com.seom.accountbook.ui.components.common.Chip
import com.seom.accountbook.ui.components.common.SideItemRow
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.util.ext.toMoney

@Composable
fun HistoryListItem(
    history: HistoryModel,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(if (selected) ColorPalette.White else Color.Transparent)
            .padding(
                start = 16.dp,
                end = 16.dp
            )
    ) {
        BaseDivider(color = ColorPalette.Purple40)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selected) {
                IconImage(
                    icon = R.drawable.ic_checkbox_checked,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Column {
                SideItemRow(
                    left = {
                        history.categoryName?.let {
                            Chip(
                                text = history.categoryName,
                                color = Color(history.categoryColor!!)
                            )
                        }
                    },
                    right = {
                        history.method?.let {
                            CustomText(
                                text = history.method,
                                style = MaterialTheme.typography.caption,
                                color = ColorPalette.Purple,
                                align = TextAlign.End
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                SideItemRow(
                    left = {
                        CustomText(
                            text = history.content,
                            style = MaterialTheme.typography.subtitle1,
                            color = ColorPalette.Purple,
                            bold = true
                        )
                    },
                    right = {
                        CustomText(
                            text = history.type.getCountOnType(history.money.toLong()).toMoney(),
                            style = MaterialTheme.typography.body2,
                            color = history.type.color,
                            bold = true
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}