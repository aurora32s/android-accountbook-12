package com.seom.accountbook.ui.screen.setting.method

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.seom.accountbook.R
import com.seom.accountbook.ui.components.OneButtonAppBar

@Composable
fun MethodAddScreen(
    methodId: String? = null,
    onBackButtonPressed: () -> Unit
) {
    val modeTitle = if (methodId.isNullOrBlank()) "추가하기" else "수정하기"
    Scaffold(topBar = {
        OneButtonAppBar(title = "결제 수단 $modeTitle") {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.clickable { onBackButtonPressed() })
        }
    }) {

    }
}