@file:OptIn(ExperimentalFoundationApi::class)

package com.seom.accountbook.ui.screen.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.model.category.Category
import com.seom.accountbook.model.method.Method
import com.seom.accountbook.ui.theme.ColorPalette

val methodMock = listOf(
    Method(id = 0, name = "현대카드"),
    Method(id = 1, name = "카카오뱅크 체크카드")
)

val outcomeMock = listOf(
    Category(id = 0, name = "교통", categoryColor = 0xFF94D3CC),
    Category(id = 0, name = "문화/여가", categoryColor = 0xFFD092E2),
    Category(id = 0, name = "미분류", categoryColor = 0xFF817DCE),
    Category(id = 0, name = "생활", categoryColor = 0xFF4A6CC3),
    Category(id = 0, name = "쇼핑/뷰티", categoryColor = 0xFF4CB8B8),
    Category(id = 0, name = "식비", categoryColor = 0xFF4CA1DE),
    Category(id = 0, name = "의료/건강", categoryColor = 0xFF6ED5EB)
)

@Composable
fun SettingScreen() {
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
                    MethodItem(method = it)
                }
                item { AddItem(itemName = "결제수단") {} }
                item { Divider(color = ColorPalette.LightPurple, thickness = 1.dp) }
                stickyHeader { Header(title = "지출 카테고리") }
                item { Divider(color = ColorPalette.LightPurple, thickness = 1.dp) }
                stickyHeader { Header(title = "수입 카테고리") }
                item { Divider(color = ColorPalette.LightPurple, thickness = 1.dp) }
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
    method: Method
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Divider(color = ColorPalette.Purple40, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp),
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
                .padding(top = 12.dp, bottom = 12.dp),
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