package com.seom.accountbook.ui.screen.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun SettingScreen() {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "설정",
                    style = MaterialTheme.typography.body1.copy(color = ColorPalette.Purple)
                )
            }
        }
    ) {
        Column {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )
        }
    }
}