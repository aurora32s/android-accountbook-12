package com.seom.accountbook.ui.screen.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seom.accountbook.R
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * 수입/지출 내역 화면 UI
 */

@Composable
fun PostScreen(
    postId: String? = null,
    onBackButtonPressed: ()->Unit
) {
    Scaffold(
        topBar = {
            OneButtonAppBar(title = if (postId.isNullOrBlank()) "내역 등록" else "내역 수정") {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onBackButtonPressed()
                    })
            }
        }
    ) {
        Column() {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )

        }
    }
}