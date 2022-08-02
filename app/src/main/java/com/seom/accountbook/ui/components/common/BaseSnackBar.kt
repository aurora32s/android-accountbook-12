package com.seom.accountbook.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun SnackbarHostState.BaseSnackBar(
    hostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = hostState
    ) { data ->
        Snackbar(
            modifier = Modifier
                .padding(16.dp),
            backgroundColor = ColorPalette.SnackBar,
            shape = RoundedCornerShape(16.dp)
        ) {
            CustomText(
                text = data.message,
                style = MaterialTheme.typography.subtitle1,
                bold = true,
                color = ColorPalette.White
            )
        }
    }
}