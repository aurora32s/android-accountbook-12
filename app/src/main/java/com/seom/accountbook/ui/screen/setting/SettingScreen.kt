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
import com.seom.accountbook.model.category.CategoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.model.method.MethodModel
import com.seom.accountbook.ui.theme.ColorPalette

val methodMock = listOf(
    MethodModel(id = 0, name = "현대카드"),
    MethodModel(id = 1, name = "카카오뱅크 체크카드")
)

val outcomeMock = listOf(
    CategoryModel(id = 0, name = "교통", categoryColor = 0xFF94D3CC),
    CategoryModel(id = 1, name = "문화/여가", categoryColor = 0xFFD092E2),
    CategoryModel(id = 2, name = "미분류", categoryColor = 0xFF817DCE),
    CategoryModel(id = 3, name = "생활", categoryColor = 0xFF4A6CC3),
    CategoryModel(id = 4, name = "쇼핑/뷰티", categoryColor = 0xFF4CB8B8),
    CategoryModel(id = 5, name = "식비", categoryColor = 0xFF4CA1DE),
    CategoryModel(id = 6, name = "의료/건강", categoryColor = 0xFF6ED5EB)
)

val incomeMock = listOf(
    CategoryModel(id = 0, name = "월급", categoryColor = 0xFF9BD182),
    CategoryModel(id = 1, name = "용돈", categoryColor = 0xFFEDCF65),
    CategoryModel(id = 2, name = "기타", categoryColor = 0xFFE29C4D)
)

@Composable
fun SettingScreen(
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
                items(items = methodMock) {
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
                items(items = outcomeMock) {
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
                items(items = incomeMock) {
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
    method: MethodModel,
    onClickItem: (Long) -> Unit
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
    category: CategoryModel,
    onClickItem: (Long) -> Unit
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
                    .background(Color(category.categoryColor))
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