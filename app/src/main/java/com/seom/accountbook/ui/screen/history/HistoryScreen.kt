package com.seom.accountbook.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seom.accountbook.AccountViewModel
import com.seom.accountbook.PostDestination
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.BackButtonTwoAppBar
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.components.button.CustomFloatingActionButton
import com.seom.accountbook.ui.components.history.HistoryList
import com.seom.accountbook.ui.components.image.IconImage
import com.seom.accountbook.ui.components.tab.TopTabRow
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.pow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen (
    mainViewModel: AccountViewModel,
    viewModel: HistoryViewModel = hiltViewModel(),
    onPushNavigate: (String, String) -> Unit
) {
    val year = mainViewModel.year.collectAsState()
    val month = mainViewModel.month.collectAsState()

    LaunchedEffect(key1 = year.value, key2 = month.value) {
        viewModel.fetchData(year.value, month.value)
    }

    val selectedItem = viewModel.selectedItem
    val histories = viewModel.histories.collectAsState()

    DateAppBar(
        year = year.value,
        month = month.value,
        onDateChange = {
            mainViewModel.setDate(it.year, it.month.value)
        }, children = {
            HistoryBody(
                histories = histories.value,
                selectedItem = selectedItem,
                onClickItem = {
                    if (selectedItem.isEmpty()) {
                        onPushNavigate(PostDestination.route, it.toString())
                    } else {
                        viewModel.setSelectedItem(it)
                    }
                },
                onLongClickItem = { viewModel.setSelectedItem(it) })
        }, header = if (selectedItem.isEmpty()) null else {
            {
                BackButtonTwoAppBar(
                    title = "${selectedItem.size}개 선택",
                    onClickBackBtn = { selectedItem.clear() }) {
                    IconImage(
                        icon = R.drawable.ic_trash,
                        modifier = Modifier.clickable { viewModel.removeItems() })
                }
            }
        }, actionButton = {
            CustomFloatingActionButton(
                onClickBtn = { onPushNavigate(PostDestination.route, "") },
                icon = R.drawable.ic_plus
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryBody(
    histories: List<HistoryModel>,
    selectedItem: MutableList<Long>,
    onClickItem: (Long) -> Unit,
    onLongClickItem: (Long) -> Unit
) {
    var currentSelectedTab by rememberSaveable {
        mutableStateOf(
            HistoryType.values().map { (2.0).pow(it.type).toInt() }.sum()
        )
    }
    Column {
        // 수입 / 지출 tab
        HistoryTopTab(
            currentSelectedTab = currentSelectedTab,
            histories = histories,
            onTabSelected = {
                currentSelectedTab = currentSelectedTab xor (2.0).pow(it.type).toInt()
            },
            modifier = Modifier.padding(16.dp)
        )
        // 수입 / 지출 리스트
        HistoryList(
            historyGroupedByDate = histories.filter {
                currentSelectedTab and (2.0).pow(it.type.type).toInt() > 0
            }.groupBy { LocalDate.of(it.year, it.month, it.date) },
            selectedItem = selectedItem,
            bottomSpacer = 60,
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
    TopTabRow(
        currentSelectedTopTab = currentSelectedTab,
        types = HistoryType.values(),
        onTabSelected = onTabSelected,
        item = { type, selected ->
            (type as? HistoryType)?.let {
                HistoryTypeItem(
                    type = type,
                    count = histories.filter { history -> type == history.type }
                        .sumOf { it.money }.toLong(),
                    selected = selected
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun HistoryTypeItem(
    type: HistoryType,
    count: Long,
    selected: Boolean,
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
            IconImage(icon = R.drawable.ic_check, color = ColorPalette.Purple)
        }
    }

    CustomText(
        text = "${stringResource(id = type.title)} ${count.toMoney(true)}",
        style = MaterialTheme.typography.subtitle2,
        color = ColorPalette.White,
        modifier = Modifier.padding(start = 5.dp)
    )
}