@file:OptIn(ExperimentalFoundationApi::class)

package com.seom.accountbook.ui.screen.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.model.category.CategoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.model.method.MethodModel
import com.seom.accountbook.ui.theme.ColorPalette

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

    Body(
        methods = methods.value,
        incomeCategories = category.value.filter { it.type == HistoryType.INCOME.type },
        outcomeCategories = category.value.filter { it.type == HistoryType.OUTCOME.type },
        onPushNavigate = onPushNavigate
    )
}

@Composable
fun Body(
    methods: List<MethodEntity>,
    incomeCategories: List<CategoryEntity>,
    outcomeCategories: List<CategoryEntity>,
    onPushNavigate: (String, String) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "설정",
                    style = MaterialTheme.typography.body1.copy(color = ColorPalette.Purple)
                )
            }
        }
    ) {
        Column {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )

            LazyColumn {
                stickyHeader { Header(title = "결제수단") }
                items(items = methods) {
                    MethodItem(method = it) {
                        onPushNavigate(
                            com.seom.accountbook.Method.route,
                            it.toString()
                        )
                    }
                }
                item {
                    AddItem(itemName = "결제수단") {
                        onPushNavigate(
                            com.seom.accountbook.Method.route,
                            ""
                        )
                    }
                }
                item { Divider(color = ColorPalette.LightPurple, thickness = 1.dp) }
                stickyHeader { Header(title = "지출 카테고리") }
                items(items = outcomeCategories) {
                    CategoryItem(category = it) {
                        onPushNavigate(
                            com.seom.accountbook.Category.route,
                            "${HistoryType.OUTCOME.type}/$it"
                        )
                    }
                }
                item {
                    AddItem(itemName = "지출 카테고리") {
                        onPushNavigate(
                            com.seom.accountbook.Category.route,
                            "${HistoryType.OUTCOME.type}"
                        )
                    }
                }
                item { Divider(color = ColorPalette.LightPurple, thickness = 1.dp) }
                stickyHeader { Header(title = "수입 카테고리") }
                items(items = incomeCategories) {
                    CategoryItem(category = it) {
                        onPushNavigate(
                            com.seom.accountbook.Category.route,
                            "${HistoryType.INCOME.type}/$it"
                        )
                    }
                }
                item {
                    AddItem(itemName = "수입 카테고리") {
                        onPushNavigate(
                            com.seom.accountbook.Category.route,
                            "${HistoryType.INCOME.type}"
                        )
                    }
                }
                item {
                    Divider(
                        color = ColorPalette.LightPurple,
                        thickness = 1.dp
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun Header(
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorPalette.OffWhite)
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body2.copy(color = ColorPalette.LightPurple)
        )
    }
}

@Composable
fun MethodItem(
    method: MethodEntity,
    onClickItem: (Long?) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Divider(color = ColorPalette.Purple40, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .clickable { onClickItem(method.id) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = method.name,
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple)
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoryEntity,
    onClickItem: (Long?) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Divider(color = ColorPalette.Purple40, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .clickable { onClickItem(category.id) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple)
            )
            Text(
                text = category.name,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight(700),
                    color = ColorPalette.White
                ),
                modifier = Modifier
                    .widthIn(56.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(category.color))
                    .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddItem(
    itemName: String,
    onClickAddButton: () -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Divider(color = ColorPalette.Purple40, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .clickable { onClickAddButton() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$itemName 추가하기",
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = ColorPalette.Purple)
            )
        }
    }
}