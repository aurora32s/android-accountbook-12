package com.seom.accountbook.ui.screen.setting.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.screen.setting.method.Input
import com.seom.accountbook.ui.screen.setting.method.InputField
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun CategoryAddScreen(
    categoryId: String? = null,
    categoryType: HistoryType,
    onBackButtonPressed: () -> Unit
) {
    val title = if (categoryType == HistoryType.INCOME) "수입" else "지출"
    val modeTitle = if (categoryId.isNullOrBlank()) "추가하기" else "수정하기"

    var name by remember { mutableStateOf("") }

    Scaffold(topBar = {
        OneButtonAppBar(title = "$title 카테고리 $modeTitle") {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.clickable { onBackButtonPressed() })
        }
    }) {
        Box {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )
            InputField(title = "이름") {
                Input(content = name, onValueChange = { name = it })
            }
        }
    }
}