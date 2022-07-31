package com.seom.accountbook.ui.screen.post

import android.os.Build
import android.view.LayoutInflater
import android.widget.GridLayout
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.exponentialDecay
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.seom.accountbook.AccountApp
import com.seom.accountbook.R
import com.seom.accountbook.model.BaseModel
import com.seom.accountbook.model.category.Category
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.model.method.Method
import com.seom.accountbook.ui.components.CustomBottomSheet
import com.seom.accountbook.ui.components.NumberPicker
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.*
import com.seom.accountbook.util.getMaxDate
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

/**
 * 수입/지출 내역 화면 UI
 */

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostScreen(
    postId: String? = null,
    viewModel: PostViewModel,
    onBackButtonPressed: () -> Unit
) {
    val postMode = postId.isNullOrBlank()
    var currentSelectedTab by remember { mutableStateOf(HistoryType.INCOME) }

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    if (postId != null) {
        viewModel.fetchAccount(postId = postId.toLong())
    }

    var current by remember { mutableStateOf(LocalDate.now()) }
    var money by remember { mutableStateOf(0) }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf<Int>(-1) }
    var method by remember { mutableStateOf<Int>(-1) }

    val year = current.year
    val month = current.month.value
    val date = current.dayOfMonth

    val maxYear = year
    val maxMonth = month
    val minYear = year - 30

    val selectYear = remember { mutableStateOf(year) }
    val selectMonth = remember { mutableStateOf(month) }
    val selectedDate = remember { mutableStateOf(date) }

    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            DatePickerBottomSheet(
                year = selectYear,
                month = selectMonth,
                date = selectedDate,
                maxYear = maxYear,
                minYear = minYear,
                onCloseBottomSheet = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                onClickConfirmBtn = {
                    current = LocalDate.of(
                        selectYear.value,
                        selectMonth.value,
                        selectedDate.value
                    )
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        }) {
        Scaffold(
            topBar = {
                OneButtonAppBar(title = if (postMode) "내역 등록" else "내역 수정") {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onBackButtonPressed()
                        })
                }
            }
        ) {
            Column {
                Divider(
                    color = ColorPalette.Purple,
                    thickness = 1.dp
                )
                PostTopTab(
                    currentSelectedTab = currentSelectedTab,
                    onTabSelected = { if (postMode) currentSelectedTab = it },
                    modifier = Modifier.padding(16.dp)
                )
                PostBody(
                    currentSelectedTab = currentSelectedTab,
                    modifier = Modifier.fillMaxHeight(),
                    onOpenDatePicker = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    date = current,
                    money = money,
                    content = content,
                    selectedCategory = category,
                    selectedMethodId = method,
                    categories = listOf(
                        Category(
                            id = 0,
                            name = "분류 1",
                            categoryColor = 0xFF524D90
                        ),
                        Category(
                            id = 1,
                            name = "분류 2",
                            categoryColor = 0xFFA79FCB
                        )
                    ),
                    methods = listOf(
                        Method(id = 0, name = "방법 1"),
                        Method(id = 1, name = "방법 2")
                    ),
                    onChangeMoney = { money = it },
                    onChangeContent = { content = it },
                    onChangeCategory = { category = it },
                    onChangeMethod = { method = it },
                    onSubmit = {
                        viewModel.addAccount()
                    }
                )
            }
        }
    }
}

// TODO 재활용할 수 있도록 수정하자
@Composable
fun PostTopTab(
    currentSelectedTab: HistoryType,
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
                    selected = it == currentSelectedTab,
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
    currentSelectedTab: HistoryType,
    modifier: Modifier = Modifier,
    onOpenDatePicker: () -> Unit,
    date: LocalDate,
    money: Int,
    content: String,
    selectedMethodId: Int,
    selectedCategory: Int,
    methods: List<Method>,
    categories: List<Category>,
    onChangeMoney: (Int) -> Unit,
    onChangeContent: (String) -> Unit,
    onChangeCategory: (Int) -> Unit,
    onChangeMethod: (Int) -> Unit,
    onSubmit: () -> Unit
) {
    val isAbleSubmit =
        money > 0 && (currentSelectedTab == HistoryType.INCOME || selectedMethodId >= 0)

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            AccountInputField(title = "일자") {
                DateInput(selectedDate = date, modifier = Modifier.clickable {
                    onOpenDatePicker()
                })
            }
            AccountInputField(title = "금액") {
                MoneyInput(
                    money = money,
                    onValueChange = { it?.let { onChangeMoney(it) } })
            }
            if (currentSelectedTab == HistoryType.OUTCOME) {
                AccountInputField(title = "결제 수단") {
                    ExposedDropdownBox(
                        selectedOptionId = selectedMethodId,
                        onOptionSelected = { onChangeMethod(it) },
                        options = methods
                    )
                }
            }
            AccountInputField(title = "분류") {
                ExposedDropdownBox(
                    selectedOptionId = selectedCategory,
                    onOptionSelected = { onChangeCategory(it) },
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
                text = "등록하기",
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
    content: String,
    onValueChange: (String) -> Unit
) {
    Box() {
        BasicTextField(
            value = content,
            onValueChange = { onValueChange(it) },
            textStyle = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple),
        )
        if (content.isBlank()) {
            Text(
                text = "입력하세요.",
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.LightPurple)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerBottomSheet(
    year: MutableState<Int>,
    month: MutableState<Int>,
    date: MutableState<Int>,
    maxYear: Int,
    minYear: Int,
    onCloseBottomSheet: () -> Unit,
    onClickConfirmBtn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 20.dp,
                bottom = 16.dp,
                end = 16.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "날짜 선택",
                style = MaterialTheme.typography.body2,
                color = ColorPalette.Purple,
                fontWeight = FontWeight(700)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorPalette.Purple),
                modifier = Modifier.clickable {
                    onCloseBottomSheet()
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberPicker(
                state = year,
                range = minYear..maxYear
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "년",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.LightPurple
            )
            Spacer(modifier = Modifier.width(8.dp))
            NumberPicker(
                state = month,
                range = 1..12
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "월",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.LightPurple
            )
            Spacer(modifier = Modifier.width(8.dp))
            NumberPicker(
                state = date,
                range = 1..month.value.getMaxDate()
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "일",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.LightPurple
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${
                    LocalDate.of(year.value, month.value, date.value).dayOfWeekText()
                }요일",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.LightPurple
            )
        }
        Button(
            onClick = { onClickConfirmBtn() },
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = ColorPalette.Yellow)
        ) {
            Text(
                text = "조회",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.White
            )
        }
    }
}

@Composable
fun ExposedDropdownBox(
    selectedOptionId: Int,
    onOptionSelected: (Int) -> Unit,
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
                    onClick = { /*TODO*/ },
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
    category: Category?,
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