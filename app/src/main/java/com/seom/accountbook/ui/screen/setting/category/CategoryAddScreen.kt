package com.seom.accountbook.ui.screen.setting.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.OneButtonAppBar

@Composable
fun CategoryAddScreen(
    categoryId: String? = null,
    categoryType: HistoryType,
    onBackButtonPressed: () -> Unit
) {
    val title = if (categoryType == HistoryType.INCOME) "수입" else "지출"
    val modeTitle = if (categoryId.isNullOrBlank()) "추가하기" else "수정하기"

    Scaffold(topBar = {
        OneButtonAppBar(title = "$title 카테고리 $modeTitle") {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.clickable { onBackButtonPressed() })
        }
    }) {

    }
}
