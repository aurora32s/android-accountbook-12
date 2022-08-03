package com.seom.accountbook.ui.components.input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.seom.accountbook.model.base.BaseModel
import com.seom.accountbook.ui.components.spinner.ExposedDropdownBox
import com.seom.accountbook.ui.components.text.InputField
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun ExposedInput(
    id: State<Long?>,
    title: String,
    values: List<BaseModel>,
    onSelectedId: (Long) -> Unit,
    onClickAddBtn: () -> Unit
) {
    InputField(name = title, textColor = ColorPalette.Purple) {
        ExposedDropdownBox(
            selectedOptionId = id.value ?: -1,
            onOptionSelected = onSelectedId,
            onPushAddButton = onClickAddBtn,
            options = values
        )
    }
}