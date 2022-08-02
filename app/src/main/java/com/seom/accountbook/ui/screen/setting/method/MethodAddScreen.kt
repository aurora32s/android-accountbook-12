package com.seom.accountbook.ui.screen.setting.method

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.ui.components.BackButtonAppBar
import com.seom.accountbook.ui.components.common.BaseSnackBar
import com.seom.accountbook.ui.components.container.BottomButtonBox
import com.seom.accountbook.ui.components.text.CustomTextField
import com.seom.accountbook.ui.theme.ColorPalette
import kotlinx.coroutines.launch

@Composable
fun MethodAddScreen(
    methodId: String? = null,
    viewModel: MethodViewModel,
    onBackButtonPressed: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = Unit) {
        viewModel.methodUiState.collect {
            when (it) {
                MethodUiState.UnInitialized -> viewModel.fetchCategory(
                    methodId = methodId?.toLong()
                )
                MethodUiState.Loading -> {}
                MethodUiState.Success.AddMethod -> {
                    onBackButtonPressed()
                }
                MethodUiState.Success.FetchMethod -> {}
                is MethodUiState.Error -> {
                    println("Error Method")
                }
                is MethodUiState.Duplicate -> {
                    this.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it.duplicateMsg
                        )
                    }
                }
            }
        }
    }
    MethodBody(
        isModifyMode = methodId.isNullOrBlank().not(),
        scaffoldState = scaffoldState,
        value = viewModel.name.collectAsState().value,
        onChangeValue = viewModel::setName,
        onClickAddBtn = viewModel::addCategory,
        onBackButtonPressed = onBackButtonPressed
    )
}

@Composable
fun MethodBody(
    isModifyMode: Boolean,
    scaffoldState: ScaffoldState,
    value: String,
    onChangeValue: (String) -> Unit,
    onClickAddBtn: () -> Unit,
    onBackButtonPressed: () -> Unit
) {
    val modeTitle = if (isModifyMode) "수정하기" else "추가하기"

    Scaffold(
        topBar = {
            BackButtonAppBar(
                title = "결제 수단 $modeTitle",
                onClickBackBtn = onBackButtonPressed
            )
        },
        snackbarHost = { it.BaseSnackBar(hostState = scaffoldState.snackbarHostState) }
    ) {
        BottomButtonBox(
            onClickBtn = onClickAddBtn,
            enabled = value.isBlank().not(),
            buttonText = "등록하기",
            buttonColor = ColorPalette.Yellow,
            disabledColor = ColorPalette.Yellow50,
            modifier = Modifier.fillMaxSize()
        ) {
            CustomTextField(
                name = "이름",
                value = value,
                textColor = ColorPalette.Purple,
                onValueChanged = onChangeValue,
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 16.dp
                )
            )
        }
    }
}