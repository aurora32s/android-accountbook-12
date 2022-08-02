package com.seom.accountbook.ui.screen.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.seom.accountbook.CategoryDestination
import com.seom.accountbook.MethodDestination
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.model.BaseModel
import com.seom.accountbook.model.category.CategoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.BackButtonAppBar
import com.seom.accountbook.ui.components.CustomBottomSheet
import com.seom.accountbook.ui.components.datesheet.FullDateBottomSheet
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.*
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * 수입/지출 내역 화면 UI
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostScreen(
    postId: String? = null,
    viewModel: PostViewModel,
    onBackButtonPressed: () -> Unit,
    onPushNavigation: (String, String) -> Unit,
) {
    val isModifyMode = postId.isNullOrBlank().not()

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

    val methods = viewModel.methods.collectAsState()
    val category = viewModel.category.collectAsState()

    Body(
        isModifyMode = isModifyMode,
        viewModel = viewModel,
        methods = methods.value,
        incomeCategories = category.value.filter { it.type == HistoryType.INCOME.type },
        outcomeCategories = category.value.filter { it.type == HistoryType.OUTCOME.type },
        onBackButtonPressed = onBackButtonPressed,
        onPushNavigation = onPushNavigation
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Body(
    isModifyMode: Boolean,
    viewModel: PostViewModel,
    methods: List<MethodEntity>,
    incomeCategories: List<CategoryEntity>,
    outcomeCategories: List<CategoryEntity>,
    onBackButtonPressed: () -> Unit,
    onPushNavigation: (String, String) -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val type = viewModel.type.collectAsState()
    val date = viewModel.date.collectAsState()
    val count = viewModel.count.collectAsState()
    val methodId = viewModel.methodId.collectAsState()
    val categoryId = viewModel.categoryId.collectAsState()
    val content = viewModel.content.collectAsState()

    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            FullDateBottomSheet(
                currentDate = date.value,
                onClickCloseBtn = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                onChangeDate = {
                    viewModel.setDate(it)
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        }) {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = if (isModifyMode) "내역 수정" else "내역 등록",
                    onClickBackBtn = onBackButtonPressed
                )
            }
        ) {
            Column {
                PostTopTab(
                    currentSelectedTab = type,
                    onTabSelected = {
                        viewModel.setType(it)
                        viewModel.setCategoryId(null)
                    },
                    modifier = Modifier.padding(16.dp)
                )
                PostBody(
                    isModifyMode = isModifyMode,
                    methods = methods,
                    categories = if (type.value == HistoryType.INCOME) incomeCategories else outcomeCategories,
                    currentSelectedTab = type,
                    openDatePicker = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    date = date,
                    count = count,
                    onChangeCount = viewModel::setCount,
                    methodId = methodId,
                    onChangeMethodId = viewModel::setMethodID,
                    categoryId = categoryId,
                    onChangeCategoryId = viewModel::setCategoryId,
                    content = content,
                    onChangeContent = viewModel::setContent,
                    onSubmit = { viewModel.addAccount() },
                    modifier = Modifier.fillMaxSize(),
                    onPushNavigation = onPushNavigation
                )
            }
        }
    }
}


// TODO 재활용할 수 있도록 수정하자
@Composable
fun PostTopTab(
    currentSelectedTab: State<HistoryType>,
    onTabSelected: (HistoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            // 수입 tab
            HistoryType.values().forEach {
                HistoryTypeItm(
                    type = it,
                    selected = it == currentSelectedTab.value,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(it) })
            }
        }
    }
}

@Composable
fun HistoryTypeItm(
    type: HistoryType,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                if (selected) ColorPalette.Purple
                else ColorPalette.LightPurple
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = type.title),
            style = MaterialTheme.typography.subtitle1,
            color = ColorPalette.White,
            modifier = Modifier.padding(
                start = 5.dp
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostBody(
    isModifyMode: Boolean,
    methods: List<MethodEntity>,
    categories: List<CategoryEntity>,
    currentSelectedTab: State<HistoryType>,
    openDatePicker: () -> Unit,
    date: State<LocalDate>,
    count: State<Int>,
    onChangeCount: (Int) -> Unit,
    methodId: State<Long?>,
    onChangeMethodId: (Long?) -> Unit,
    categoryId: State<Long?>,
    onChangeCategoryId: (Long?) -> Unit,
    content: State<String>,
    onChangeContent: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit,
    onPushNavigation: (String, String) -> Unit
) {
    val isAbleSubmit =
        count.value > 0 && (currentSelectedTab.value == HistoryType.INCOME || methodId.value != null)

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            AccountInputField(title = "일자") {
                DateInput(selectedDate = date.value, modifier = Modifier.clickable {
                    openDatePicker()
                })
            }
            AccountInputField(title = "금액") {
                MoneyInput(
                    money = count.value,
                    onValueChange = { it?.let { onChangeCount(it) } })
            }
            if (currentSelectedTab.value == HistoryType.OUTCOME) {
                AccountInputField(title = "결제 수단") {
                    ExposedDropdownBox(
                        selectedOptionId = methodId.value ?: -1,
                        onOptionSelected = onChangeMethodId,
                        onPushAddButton = {
                            onPushNavigation(
                                MethodDestination.route,
                                ""
                            )
                        },
                        options = methods
                    )
                }
            }
            AccountInputField(title = "분류") {
                ExposedDropdownBox(
                    selectedOptionId = categoryId.value ?: -1,
                    onOptionSelected = onChangeCategoryId,
                    onPushAddButton = {
                        onPushNavigation(
                            CategoryDestination.route,
                            "${currentSelectedTab.value.type}"
                        )
                    },
                    options = categories
                )
            }
            AccountInputField(title = "내용") {
                ContentInput(content = content, onValueChange = onChangeContent)
            }
        }
        Button(
            onClick = { onSubmit() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorPalette.Yellow,
                disabledBackgroundColor = ColorPalette.Yellow50
            ),
            enabled = isAbleSubmit
        ) {
            Text(
                text = if (isModifyMode) "수정하기" else "등록하기",
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight(700),
                    color = ColorPalette.White
                ),
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .background(Color.Transparent),
            )
        }
    }
}

@Composable
fun AccountInputField(
    title: String,
    input: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight(500),
            color = ColorPalette.Purple,
            modifier = Modifier.weight(2f)
        )
        Surface(
            modifier = Modifier
                .weight(8f)
                .padding(start = 8.dp),
            color = Color.Transparent
        ) {
            input()
        }
    }
    Divider(
        color = ColorPalette.Purple40,
        thickness = 1.dp
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateInput(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier
) {
    Text(
        text = selectedDate.fullFormat(),
        style = MaterialTheme.typography.caption,
        color = ColorPalette.Purple,
        modifier = modifier
    )
}

@Composable
fun MoneyInput(
    money: Int,
    onValueChange: (Int?) -> Unit
) {
    Box() {
        BasicTextField(
            value = if (money != 0) money.toMoney() else "",
            onValueChange = {
                onValueChange(
                    if (it.isNullOrBlank()) 0
                    else it.replace(",", "").toIntOrNull()
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple)
        )
        if (money == 0) {
            Text(
                text = "입력하세요.",
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.LightPurple)
            )
        }
    }
}

@Composable
fun ContentInput(
    content: State<String>,
    onValueChange: (String) -> Unit
) {
    Box() {
        BasicTextField(
            value = content.value,
            onValueChange = { onValueChange(it) },
            textStyle = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple),
        )
        if (content.value.isBlank()) {
            Text(
                text = "입력하세요.",
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.LightPurple)
            )
        }
    }
}

@Composable
fun ExposedDropdownBox(
    selectedOptionId: Long,
    onOptionSelected: (Long?) -> Unit,
    onPushAddButton: () -> Unit,
    options: List<BaseModel>
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = options.find { it.id == selectedOptionId }
    var dropDownWidth by remember { mutableStateOf(Size.Zero) }
    var rotateDegree by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    rotateDegree = 180f
                    expanded = true
                }
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    dropDownWidth = coordinates.size.toSize()
                },
        ) {
            Text(
                text = selectedOption?.name ?: "선택하세요.",
                style = MaterialTheme.typography.caption.copy(
                    color = if (selectedOption == null) ColorPalette.LightPurple
                    else ColorPalette.Purple
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = ColorPalette.Purple),
                modifier = Modifier.rotate(rotateDegree)
            )
        }

        MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(14.dp))) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    rotateDegree = 0f
                    expanded = false
                },
                offset = DpOffset(x = 0.dp, y = 8.dp),
                modifier = Modifier
                    .width(with(LocalDensity.current) { dropDownWidth.width.toDp() })
                    .border(
                        width = 1.dp,
                        color = ColorPalette.Purple,
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                options.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(it.id)
                            expanded = false
                        },
                        modifier = Modifier
                            .height(36.dp)
                            .background(Color.Transparent),
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.subtitle1.copy(color = ColorPalette.Purple)
                        )
                    }
                }
                DropdownMenuItem(
                    onClick = { onPushAddButton() },
                    modifier = Modifier
                        .height(36.dp)
                        .background(Color.Transparent),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "추가하기",
                            style = MaterialTheme.typography.subtitle1.copy(color = ColorPalette.Purple)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = ColorPalette.Purple),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: CategoryModel?,
    modifier: Modifier = Modifier
) {
    Text(
        text = category?.name ?: "옵션을 선택해주세요.",
        modifier = modifier
            .widthIn(56.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(
                if (category != null) Color(category.categoryColor)
                else ColorPalette.Purple
            )
            .padding(
                start = 8.dp,
                top = 4.dp,
                bottom = 4.dp,
                end = 8.dp
            ),
        style = MaterialTheme.typography.subtitle2,
        color = ColorPalette.White,
        textAlign = TextAlign.Center
    )
}