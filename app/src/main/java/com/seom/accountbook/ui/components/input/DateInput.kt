package com.seom.accountbook.ui.components.input

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.components.text.InputField
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.fullFormat
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateInput(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier
) {
    InputField(name = "일자", textColor = ColorPalette.Purple) {
        CustomText(
            text = selectedDate.fullFormat(),
            style = MaterialTheme.typography.subtitle1,
            color = ColorPalette.Purple,
            modifier = modifier
        )
    }
}
