package com.seom.accountbook.ui.screen.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seom.accountbook.AccountViewModel
import com.seom.accountbook.DetailDestination
import com.seom.accountbook.model.graph.OutComeByCategory
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.components.common.BottomSpacer
import com.seom.accountbook.ui.components.common.Chip
import com.seom.accountbook.ui.components.common.SideItemRow
import com.seom.accountbook.ui.components.graph.CircleGraph
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GraphScreen(
    mainViewModel: AccountViewModel,
    onDateChange: (Int, Int) -> Unit,
    viewModel: GraphViewModel,
    onPushNavigate: (String, String) -> Unit
) {
    val year = mainViewModel.year.collectAsState()
    val month = mainViewModel.month.collectAsState()

    LaunchedEffect(key1 = year.value, key2 = month.value) {
        viewModel.fetchData(year.value, month.value)
    }

    val accounts = viewModel.outcome.collectAsState()
    val totalCount = accounts.value.sumOf { it.count }

    DateAppBar(
        year = year.value,
        month = month.value,
        onDateChange = {
            onDateChange(it.year, it.month.value)
        },
        children = {
            GraphBody(totalCount = totalCount, accounts = accounts.value, onClickItem = {
                onPushNavigate(
                    DetailDestination.route,
                    "$it/${year.value}/${month.value}"
                )
            })
        }
    )
}

@Composable
fun GraphBody(
    totalCount: Long,
    accounts: List<OutComeByCategory>,
    onClickItem: (Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BaseDivider(color = ColorPalette.Purple)
        TopRow(totalCount = totalCount)
        BaseDivider(color = ColorPalette.LightPurple)
        Box(modifier = Modifier.fillMaxSize()) {
            if (accounts.isEmpty()) {
                Text(
                    text = "내역이 없습니다.",
                    style = MaterialTheme.typography.subtitle1.copy(color = ColorPalette.Purple),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))
                    CircleGraph(
                        data = accounts,
                        totalCount = totalCount,
                        modifier = Modifier
                            .height(254.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    CategoryList(
                        data = accounts,
                        totalCount = totalCount,
                        onItemClick = onClickItem
                    )
                }
            }
        }
    }
}

@Composable
fun TopRow(
    totalCount: Long
) {
    SideItemRow(
        modifier = Modifier.padding(start = 16.dp, top = 9.dp, end = 16.dp, bottom = 9.dp),
        left = {
            CustomText(
                text = "이번 달 총 지출 금액",
                style = MaterialTheme.typography.subtitle1,
                color = ColorPalette.Purple,
                bold = true
            )
        },
        right = {
            CustomText(
                text = totalCount.toMoney(true),
                style = MaterialTheme.typography.subtitle1,
                color = ColorPalette.Red,
                bold = true
            )
        }
    )
}

@Composable
fun CategoryList(
    data: List<OutComeByCategory>,
    totalCount: Long,
    onItemClick: (Long) -> Unit
) {
    LazyColumn(
    ) {
        items(items = data) { row ->
            CategoryOutComeItem(
                data = row,
                totalCount = totalCount,
                modifier = Modifier.clickable { onItemClick(row.id) })
        }
        BottomSpacer(40)
    }
}

@Composable
fun CategoryOutComeItem(
    data: OutComeByCategory,
    totalCount: Long,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        SideItemRow(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            left = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Chip(
                        text = data.name,
                        color = Color(if (data.color == 0L) 0xFF2E2E2E else data.color)
                    )
                    CustomText(
                        text = data.count.toMoney(),
                        style = MaterialTheme.typography.subtitle1,
                        color = ColorPalette.Purple,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            },
            right = {
                CustomText(
                    text = "${((data.count / totalCount.toFloat()) * 100).toInt()}%",
                    style = MaterialTheme.typography.subtitle1,
                    color = ColorPalette.Purple
                )
            }
        )
        BaseDivider(color = ColorPalette.Purple40)
    }
}