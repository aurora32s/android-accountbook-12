package com.seom.accountbook.ui.screen.category

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.container.BackBottomButtonBox
import com.seom.accountbook.ui.components.header.SingleTextHeader
import com.seom.accountbook.ui.components.selector.ColorSelector
import com.seom.accountbook.ui.components.text.CustomTextField
import com.seom.accountbook.ui.theme.ColorPalette
import kotlinx.coroutines.launch

@Composable
fun CategoryAddScreen(
    categoryId: Long? = null,
    categoryType: HistoryType,
    viewModel: CategoryViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = Unit) {
        viewModel.categoryUiState.collect {
            when (it) {
                CategoryUiState.UnInitialized -> viewModel.fetchCategory(
                    categoryId = categoryId,
                    categoryType = categoryType
                )
                CategoryUiState.Loading -> {}
                CategoryUiState.Success.AddCategory -> onBackPressed()
                CategoryUiState.Success.FetchCategory -> {}
                is CategoryUiState.Error -> {}
                is CategoryUiState.Duplicate -> {
                    this.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it.duplicateMsg
                        )
                    }
                }
            }
        }
    }
    SettingBody(
        isModifyMode = categoryId?.let { true } ?: false,
        scaffoldState = scaffoldState,
        colorList = viewModel.colorList,
        categoryType = categoryType,
        name = viewModel.name.collectAsState().value,
        color = viewModel.color.collectAsState().value,
        onChangeName = viewModel::setName,
        onChangeColor = viewModel::setColor,
        onClickAddBtn = viewModel::addCategory,
        onBackButtonPressed = onBackPressed
    )
}

@Composable
fun SettingBody(
    isModifyMode: Boolean,
    scaffoldState: ScaffoldState,
    colorList: List<Long>,
    categoryType: HistoryType,
    name: String,
    color: Long,
    onChangeName: (String) -> Unit,
    onChangeColor: (Long) -> Unit,
    onClickAddBtn: () -> Unit,
    onBackButtonPressed: () -> Unit
) {
    val title = if (categoryType == HistoryType.INCOME) "수입" else "지출"
    val modeTitle = if (isModifyMode) "추가하기" else "수정하기"

    BackBottomButtonBox(
        scaffoldState = scaffoldState,
        enabled = name.isBlank().not(),
        appbarTitle = "$title 카테고리 $modeTitle",
        buttonTitle = "등록하기",
        buttonColor = ColorPalette.Yellow,
        disabledButtonColor = ColorPalette.Yellow50,
        onClickBackBtn = onBackButtonPressed,
        onClickBottomBtn = onClickAddBtn
    ) {
        CustomTextField(
            name = "이름",
            value = name,
            textColor = ColorPalette.Purple,
            onValueChanged = onChangeName,
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        SingleTextHeader(title = "색상")
        ColorSelector(
            colors = colorList,
            perLine = 10,
            selectedColor = color,
            onSelectItem = onChangeColor
        )
        Divider(color = ColorPalette.LightPurple, thickness = 1.dp)
    }
}
