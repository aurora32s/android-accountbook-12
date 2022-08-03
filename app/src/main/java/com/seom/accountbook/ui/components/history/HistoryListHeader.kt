package com.seom.accountbook.ui.components.history

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.header.BaseTextHeader
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney

@Composable
fun HistoryListHeader(
    date: String,
    income: Int,
    outCome: Int
) {
    BaseTextHeader {
        CustomText(
            text = date,
            style = MaterialTheme.typography.body2,
            bold = false,
            color = ColorPalette.LightPurple
        )
        Spacer(modifier = Modifier.weight(1f))
        if (income > 0) {
            CountItem(title = "수입", count = income)
        }
        Spacer(modifier = Modifier.width(2.dp))
        if (outCome > 0) {
            CountItem(title = "지출", count = outCome)
        }
    }
}

@Composable
fun CountItem(
    title: String,
    count: Int
) {
    CustomText(
        text = "$title ${count.toMoney()}원",
        style = MaterialTheme.typography.caption,
        color = ColorPalette.LightPurple
    )
}