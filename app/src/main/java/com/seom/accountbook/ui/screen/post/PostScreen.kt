package com.seom.accountbook.ui.screen.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.seom.accountbook.CategoryDestination
import com.seom.accountbook.MethodDestination
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.container.BackBottomButtonBox
import com.seom.accountbook.ui.components.datesheet.FullDateBottomSheet
import com.seom.accountbook.ui.components.input.ContentInput
import com.seom.accountbook.ui.components.input.DateInput
import com.seom.accountbook.ui.components.input.ExposedInput
import com.seom.accountbook.ui.components.input.MoneyInput
import com.seom.accountbook.ui.components.tab.TopTabRow
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette
import kotlinx.coroutines.launch

/**
 * 수입/지출 내역 화면 UI
 */
@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostScreen(
    postId: String? = null,
    viewModel: PostViewModel,
    onBackButtonPressed: () -> Unit,
    onPushNavigation: (String, String) -> Unit,
) {
    val isModifyMode = postId.isNullOrBlank().not()
    val scaffoldState = rememberScaffoldState()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        viewModel.postUiState.collect {
            when (it) {
                PostUiState.UnInitialized -> viewModel.fetchAccount(postId = postId?.toLong())
                PostUiState.Loading -> {}
                is PostUiState.Success.FetchAccount -> {}
                PostUiState.Success.AddAccount -> onBackButtonPressed()
                is PostUiState.Error -> {}
            }
        }
    }

    FullDateBottomSheet(
        sheetState = bottomSheetState,
        onClickCloseBtn = { coroutine.launch { bottomSheetState.hide() } },
        onChangeDate = viewModel::setDate
    ) {
        PostBody(
            isModifyMode = isModifyMode,
            scaffoldState = scaffoldState,
            viewModel = viewModel,
            onOpenDatePicker = { coroutine.launch { bottomSheetState.show() }},
            onBackButtonPressed = onBackButtonPressed,
            onPushNavigation = onPushNavigation
        )
    }
}

@Composable
fun PostTopTab(
    currentSelectedTab: Int,
    onTabSelected: (HistoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    TopTabRow(
        currentSelectedTopTab = currentSelectedTab,
        types = HistoryType.values(),
        onTabSelected = onTabSelected,
        item = { type, _ ->
            (type as? HistoryType)?.let {
                CustomText(
                    text = stringResource(id = type.title),
                    style = MaterialTheme.typography.subtitle2,
                    color = ColorPalette.White,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        },
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostBody(
    isModifyMode: Boolean,
    scaffoldState: ScaffoldState,
    viewModel: PostViewModel,
    onOpenDatePicker: () -> Unit,
    onBackButtonPressed: () -> Unit,
    onPushNavigation: (String, String) -> Unit
) {
    val date = viewModel.date.collectAsState()
    val count = viewModel.count.collectAsState()
    val methodId = viewModel.methodId.collectAsState()
    val categoryId = viewModel.categoryId.collectAsState()
    val content = viewModel.content.collectAsState()
    val type = viewModel.type.collectAsState()

    val scrollState = rememberScrollState()
    val isAbleAdd = count.value > 0 && methodId.value != null && content.value.isNotBlank()

    BackBottomButtonBox(
        scaffoldState = scaffoldState,
        enabled = isAbleAdd,
        appbarTitle = if (isModifyMode) "내역 수정" else "내역 등록",
        buttonTitle = if (isModifyMode) "수정하기" else "등록하기",
        buttonColor = ColorPalette.Yellow,
        disabledButtonColor = ColorPalette.Yellow50,
        onClickBackBtn = onBackButtonPressed,
        onClickBottomBtn = viewModel::addAccount
    ) {
        PostTopTab(
            currentSelectedTab = viewModel.type.collectAsState().value.type,
            onTabSelected = {
                viewModel.setType(it)
                viewModel.setCategoryId(null)
            },
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(scrollState)
        ) {
            DateInput(
                selectedDate = date.value,
                modifier = Modifier.clickable { onOpenDatePicker() })
            Spacer(modifier = Modifier.height(16.dp))
            MoneyInput(
                money = count.value,
                onValueChange = { it?.let { viewModel.setCount(it) } })
            Spacer(modifier = Modifier.height(16.dp))
            ExposedInput(
                id = methodId,
                title = "결제수단",
                values = viewModel.methods.collectAsState().value,
                onSelectedId = viewModel::setMethodID,
                onClickAddBtn = {
                    onPushNavigation(
                        MethodDestination.route,
                        ""
                    )
                })
            Spacer(modifier = Modifier.height(16.dp))
            ExposedInput(
                id = categoryId,
                title = "분류",
                values = viewModel.category.collectAsState().value,
                onSelectedId = viewModel::setCategoryId,
                onClickAddBtn = {
                    onPushNavigation(
                        CategoryDestination.route,
                        "${type.value.type}"
                    )
                })
            Spacer(modifier = Modifier.height(16.dp))
            ContentInput(
                content = content.value,
                onValueChange = viewModel::setContent
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
