package com.seom.accountbook.ui.components.container

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.seom.accountbook.ui.components.BackButtonOneAppBar
import com.seom.accountbook.ui.components.common.BaseSnackBar

@Composable
fun BackBottomButtonBox(
    scaffoldState: ScaffoldState,
    enabled: Boolean,
    appbarTitle: String,
    buttonTitle: String,
    buttonColor: Color,
    disabledButtonColor: Color,
    onClickBackBtn: () -> Unit,
    onClickBottomBtn: () -> Unit,
    body: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            BackButtonOneAppBar(
                title = appbarTitle,
                onClickBackBtn = onClickBackBtn
            )
        },
        snackbarHost = { it.BaseSnackBar(hostState = scaffoldState.snackbarHostState) }
    ) {
        BottomButtonBox(
            onClickBtn = onClickBottomBtn,
            enabled = enabled,
            buttonText = buttonTitle,
            buttonColor = buttonColor,
            disabledColor = disabledButtonColor,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                body()
            }
        }
    }
}