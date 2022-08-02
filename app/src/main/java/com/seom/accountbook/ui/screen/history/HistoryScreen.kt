package com.seom.accountbook.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seom.accountbook.Post
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.components.TwoButtonAppBar
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.fullFormat
import com.seom.accountbook.util.ext.toMoney
import java.time.LocalDate
import kotlin.math.pow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onPushNavigate: (String, String) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        val current = LocalDate.now()

        val year = current.year
        val month = current.month.value
        viewModel.fetchData(year, month)
    }

    val selectedItem = remember { viewModel.selectedItem }
    var histories = viewModel.histories.collectAsState()

    DateAppBar(
        onDateChange = { date ->
            // TODO 변경된 날짜에 맞는 데이터 요청
            viewModel.fetchData(date.year, date.month.value)
        }, children = {
            HistoryBody(
                histories = histories.value,
                selectedItem = selectedItem,
                onClickItem = {
                    if (selectedItem.isEmpty()) {
                        // TODO 내역 수정 화면으로 이동
                        onPushNavigate(Post.route, it.toString())
                    } else {
                        if (it in selectedItem) viewModel.removeSelectedItem(it)
                        else viewModel.addSelectedItem(it)
                    }
                },
                onLongClickItem = {
                    if (it in selectedItem) viewModel.removeSelectedItem(it)
                    else viewModel.addSelectedItem(it)
                })
        }, header = if (selectedItem.isEmpty()) null else {
            {
                TwoButtonAppBar(
                    leftIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                selectedItem.clear()
                            }
                        )
                    },
                    rightIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_trash),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                // TODO 선택된 내역 삭제 요청
                                viewModel.removeItems()
                            }
                        )
                    },
                    title = "${selectedItem.size}개 선택"
                )
            }
        }, actionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO 내역 추가 화면으로 이동
                    onPushNavigate(Post.route, "")
                },
                backgroundColor = ColorPalette.Yellow
            ) {
                Image(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
            }
        })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryBody(
    histories: List<HistoryModel>,
    selectedItem: MutableList<Long>,
    onClickItem: (Long) -> Unit,
    onLongClickItem: (Long) -> Unit
) {
    var currentSelectedTab by remember {
        mutableStateOf(
            (2.0).pow(HistoryType.INCOME.type).toInt()
        )
    }
    Column() {
        Divider(
            color = ColorPalette.Purple,
            thickness = 1.dp
        )
        // 수입 / 지출 tab
        HistoryTopTab(
            currentSelectedTab = currentSelectedTab,
            histories = histories,
            onTabSelected = {
                currentSelectedTab = currentSelectedTab xor (2.0).pow(it.type).toInt()
            },
            modifier = Modifier
                .padding(16.dp)
        )
        // 수입 / 지출 리스트
        HistoryList(
            historyGroupedByDate = histories.filter {
                currentSelectedTab and (2.0).pow(it.type.type).toInt() > 0
            }.groupBy { LocalDate.of(it.year, it.month, it.date) },
            selectedItem = selectedItem,
            currentType = currentSelectedTab,
            modifier = Modifier.fillMaxWidth(),
            onClickItem = { onClickItem(it) },
            onLongClickItem = { onLongClickItem(it) }
        )
    }
}

@Composable
fun HistoryTopTab(
    currentSelectedTab: Int,
    histories: List<HistoryModel>,
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
            HistoryType.values().forEach { type ->
                HistoryTypeItem(
                    type = type,
                    count = histories.filter { history -> type == history.type }
                        .sumOf { it.money }.toLong(),
                    selected = ((2.0).pow(type.type).toInt() and currentSelectedTab) > 0,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onTabSelected(type)
                        }
                )
            }
        }
    }
}

@Composable
fun HistoryTypeItem(
    type: HistoryType,
    count: Long,
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
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (selected) ColorPalette.White else Color.Transparent)
                .border(
                    width = 1.dp,
                    color = ColorPalette.Purple,
                    shape = RoundedCornerShape(2.dp)
                )
        ) {
            if (selected) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = ColorPalette.Purple)
                )
            }
        }

        Text(
            text = "${stringResource(id = type.title)} ${count.toMoney()}원",
            style = MaterialTheme.typography.subtitle1,
            color = ColorPalette.White,
            modifier = Modifier.padding(
                start = 5.dp
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryList(
    // TODO Key 를 String 으로 두는게 맞을까...
    historyGroupedByDate: Map<LocalDate, List<HistoryModel>>,
    selectedItem: MutableList<Long>,
    currentType: Int,
    modifier: Modifier = Modifier,
    onClickItem: (Long) -> Unit,
    onLongClickItem: (Long) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (historyGroupedByDate.size == 0) {
            Text(
                text = "내역이 없습니다.",
                style = MaterialTheme.typography.subtitle1.copy(color = ColorPalette.Purple),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = modifier
            ) {
                historyGroupedByDate.forEach { (date, histories) ->
                    item {
                        HistoryListHeader(
                            date = date.fullFormat(),
                            income = histories.filter { it.type == HistoryType.INCOME }
                                .sumOf { it.money },
                            outCome = histories.filter { it.type == HistoryType.OUTCOME }
                                .sumOf { it.money }
                        )
                    }
                    items(items = histories) { history ->
                        HistoryListItem(
                            history = history,
                            selected = history.id in selectedItem,
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = { onClickItem(history.id) },
                                    onLongClick = { onLongClickItem(history.id) }
                                )
                        )
                    }
                    item {
                        Divider(
                            color = ColorPalette.LightPurple,
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryListHeader(
    date: String,
    income: Int,
    outCome: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorPalette.OffWhite)
            .padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 8.dp,
                end = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.body2,
            color = ColorPalette.LightPurple
        )
        Spacer(modifier = Modifier.weight(1f))
        if (income > 0) {
            Text(
                text = "수입 ${income.toMoney()}원",
                style = MaterialTheme.typography.subtitle1,
                color = ColorPalette.LightPurple
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        if (outCome > 0) {
            Text(
                text = "지출 ${outCome.toMoney()}원",
                style = MaterialTheme.typography.subtitle1,
                color = ColorPalette.LightPurple
            )
        }
    }
}

@Composable
fun HistoryListItem(
    history: HistoryModel,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(if (selected) ColorPalette.White else Color.Transparent)
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
    ) {
        Divider(
            color = ColorPalette.Purple40,
            thickness = 1.dp
        )
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selected) {
                Image(
                    painter = painterResource(id = R.drawable.ic_checkbox_checked),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Column(
//                modifier = Modifier.padding(start = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    history.categoryName?.let {
                        Text(
                            text = history.categoryName,
                            modifier = Modifier
                                .widthIn(56.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(history.categoryColor!!))
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
                    history.method?.let {
                        Text(
                            text = history.method,
                            style = MaterialTheme.typography.subtitle1,
                            color = ColorPalette.Purple,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = history.content,
                        style = MaterialTheme.typography.caption,
                        color = ColorPalette.Purple
                    )
                    Text(
                        text = "${
                            if (history.type == HistoryType.OUTCOME) (-1 * history.money).toMoney()
                            else history.money.toMoney()
                        } 원",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight(700),
                        color = if (history.type == HistoryType.INCOME) ColorPalette.Green else ColorPalette.Red
                    )
                }
            }
        }
    }
}