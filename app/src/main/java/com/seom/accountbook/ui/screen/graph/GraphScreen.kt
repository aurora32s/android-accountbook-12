package com.seom.accountbook.ui.screen.graph

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seom.accountbook.model.graph.OutComeByCategoryModel
import com.seom.accountbook.ui.AccountViewModel
import com.seom.accountbook.ui.components.DateAppBar
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.components.common.BottomSpacer
import com.seom.accountbook.ui.components.common.Chip
import com.seom.accountbook.ui.components.common.SideItemRow
import com.seom.accountbook.ui.components.graph.CircleGraph
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.screen.detail.DetailDestination
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.toMoney

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GraphScreen(
    mainViewModel: AccountViewModel,
    viewModel: GraphViewModel = hiltViewModel(),
    navigate: (String, String) -> Unit
) {
    val year = mainViewModel.year.collectAsState()
    val month = mainViewModel.month.collectAsState()
    println("$year $month")

    LaunchedEffect(key1 = year.value, key2 = month.value) {
        viewModel.fetchData(year.value, month.value)
    }

    val accounts = viewModel.outcome.collectAsState()
    val totalCount = accounts.value.sumOf { it.count }

    DateAppBar(
        year = year.value,
        month = month.value,
        onDateChange = {
            mainViewModel.setDate(it.year, it.month.value)
        },
        children = {
            GraphBody(totalCount = totalCount, accounts = accounts.value, onClickItem = {
                navigate(
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
    accounts: List<OutComeByCategoryModel>,
    onClickItem: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopRow(totalCount = totalCount)
        BaseDivider(color = ColorPalette.LightPurple)
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            if (accounts.isEmpty()) {
                CustomText(
                    text = "????????? ????????????.",
                    style = MaterialTheme.typography.subtitle2,
                    color = ColorPalette.Purple,
                    bold = true,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                val localConfig = LocalConfiguration.current

                when (localConfig.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        Row {
                            GraphContent(
                                accounts = accounts,
                                totalCount = totalCount,
                                onClickItem = onClickItem,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .padding(bottom = 24.dp),
                                modifierForList = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            )
                        }
                    } // ??????
                    Configuration.ORIENTATION_PORTRAIT -> {
                        Column {
                            GraphContent(
                                accounts = accounts,
                                totalCount = totalCount,
                                onClickItem = onClickItem,
                                modifier = Modifier
                                    .height(254.dp)
                                    .fillMaxWidth()
                            )
                        }
                    } // ??????
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun GraphContent(
    accounts: List<OutComeByCategoryModel>,
    totalCount: Long,
    onClickItem: (Long) -> Unit,
    modifier: Modifier = Modifier,
    modifierForList: Modifier = Modifier
) {
    CircleGraph(
        data = accounts,
        totalCount = totalCount,
        modifier = modifier
    )
    Spacer(modifier = Modifier.height(24.dp))
    CategoryList(
        data = accounts,
        totalCount = totalCount,
        onItemClick = onClickItem,
        modifier = modifierForList
    )
}

@Composable
fun TopRow(
    totalCount: Long
) {
    SideItemRow(
        modifier = Modifier.padding(start = 16.dp, top = 9.dp, end = 16.dp, bottom = 9.dp),
        left = {
            CustomText(
                text = "?????? ??? ??? ?????? ??????",
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
    data: List<OutComeByCategoryModel>,
    totalCount: Long,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
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
    data: OutComeByCategoryModel,
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
                        color = Color(data.color)
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