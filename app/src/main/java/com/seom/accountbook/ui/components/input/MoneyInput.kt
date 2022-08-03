package com.seom.accountbook.ui.components.input

import androidx.compose.runtime.Composable
import com.seom.accountbook.ui.components.text.CustomTextField
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney

@Composable
fun MoneyInput(
    money: Int,
    onValueChange: (Int?) -> Unit
) {
    CustomTextField(
        name = "금액",
        value = if (money != 0) money.toMoney() else "",
        textColor = ColorPalette.Purple,
        onlyNumber = true,
        onValueChanged = {
            onValueChange(
                if (it.isBlank()) 0
                else it.replace(",", "").toIntOrNull()
            )
        }
    )
}