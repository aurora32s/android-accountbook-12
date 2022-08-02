@file:OptIn(ExperimentalFoundationApi::class)

package com.seom.accountbook.ui.screen.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seom.accountbook.AccountDestination
import com.seom.accountbook.CategoryDestination
import com.seom.accountbook.MethodDestination
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.model.BaseModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.appbar.NoneButtonAppBar
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.components.common.BottomSpacer
import com.seom.accountbook.ui.components.header.SingleTextHeader
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.R
import com.seom.accountbook.ui.components.common.Chip
import com.seom.accountbook.ui.components.image.IconImage

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    onPushNavigate: (String, String) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData()
    }

    val methods = viewModel.methods.collectAsState()
    val category = viewModel.category.collectAsState()

    SettingBody(
        methods = methods.value,
        incomeCategories = category.value.filter { it.type == HistoryType.INCOME.type },
        outcomeCategories = category.value.filter { it.type == HistoryType.OUTCOME.type },
        onPushNavigate = onPushNavigate
    )
}

@Composable
fun SettingBody(
    methods: List<MethodEntity>,
    incomeCategories: List<CategoryEntity>,
    outcomeCategories: List<CategoryEntity>,
    onPushNavigate: (String, String) -> Unit
) {
    Scaffold(
        topBar = {
            NoneButtonAppBar(title = "설정")
        }
    ) {
        LazyColumn {
            SettingGroup(
                items = methods,
                itemName = "결제수단",
                destination = MethodDestination,
                onPushNavigate = onPushNavigate
            )
            SettingGroup(
                items = outcomeCategories,
                itemName = "지출 카테고리",
                destination = CategoryDestination,
                onPushNavigate = { route, id ->
                    onPushNavigate(
                        route, "${HistoryType.OUTCOME.type}${if (id.isBlank()) "" else "/$id"}"
                    )
                },
                rightChild = {
                    (it as? CategoryEntity)?.let { category ->
                        Chip(text = category.name, color = Color(category.color))
                    }
                }
            )
            SettingGroup(
                items = incomeCategories,
                itemName = "수입 카테고리",
                destination = CategoryDestination,
                onPushNavigate = { route, id ->
                    onPushNavigate(
                        route, "${HistoryType.INCOME.type}${if (id.isBlank()) "" else "/$id"}"
                    )
                },
                rightChild = {
                    (it as? CategoryEntity)?.let { category ->
                        Chip(text = category.name, color = Color(category.color))
                    }
                }
            )
            BottomSpacer(40)
        }
    }
}

fun LazyListScope.SettingGroup(
    items: List<BaseModel>,
    itemName: String,
    rightChild: @Composable (BaseModel) -> Unit = {},
    destination: AccountDestination,
    onPushNavigate: (String, String) -> Unit
) {
    stickyHeader { SingleTextHeader(title = itemName) }
    items(items = items) { item ->
        SettingItem(
            itemName = item.name,
            onClickItem = {
                onPushNavigate(destination.route, item.id?.toString() ?: "")
            }
        ) {
            rightChild(item)
        }
    }
    item {
        SettingItem(
            itemName = "$itemName 추가하기",
            onClickItem = { onPushNavigate(destination.route, "") }
        ) {
            IconImage(icon = R.drawable.ic_plus, color = ColorPalette.Purple)
        }
        Divider(color = ColorPalette.LightPurple, thickness = 1.dp)
    }
}

@Composable
fun SettingItem(
    itemName: String,
    onClickItem: () -> Unit,
    rightChild: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 10.dp)
    ) {
        BaseDivider(color = ColorPalette.Purple40)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .clickable { onClickItem() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomText(
                text = itemName,
                style = MaterialTheme.typography.subtitle1,
                bold = true,
                color = ColorPalette.Purple
            )
            rightChild()
        }
    }
}