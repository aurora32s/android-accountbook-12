package com.seom.accountbook.ui.screen.setting.method

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.container.BackBottomButtonBox
import com.seom.accountbook.ui.components.text.CustomTextField
import com.seom.accountbook.ui.theme.ColorPalette
import kotlinx.coroutines.launch

@Composable
fun MethodAddScreen(
    methodId: String? = null,
    methodType: HistoryType,
    viewModel: MethodViewModel,
    onBackButtonPressed: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = Unit) {
        viewModel.methodUiState.collect {
            when (it) {
                MethodUiState.UnInitialized -> viewModel.fetchCategory(
                    methodId = methodId?.toLong(),
                    methodType = methodType
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
        methodType = methodType,
        value = viewModel.name.collectAsState().value,
        onChangeValue = viewModel::setName,
        onClickAddBtn = viewModel::addMethod,
        onBackButtonPressed = onBackButtonPressed
    )
}

@Composable
fun MethodBody(
    isModifyMode: Boolean,
    scaffoldState: ScaffoldState,
    methodType: HistoryType,
    value: String,
    onChangeValue: (String) -> Unit,
    onClickAddBtn: () -> Unit,
    onBackButtonPressed: () -> Unit
) {
    val title = if (methodType == HistoryType.INCOME) "입금계좌" else "결제수단"
    val modeTitle = if (isModifyMode) "수정하기" else "추가하기"

    BackBottomButtonBox(
        scaffoldState = scaffoldState,
        enabled = value.isBlank().not(),
        appbarTitle = "$title $modeTitle",
        buttonTitle = "등록하기",
        buttonColor = ColorPalette.Yellow,
        disabledButtonColor = ColorPalette.Yellow50,
        onClickBackBtn = onBackButtonPressed,
        onClickBottomBtn = onClickAddBtn
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