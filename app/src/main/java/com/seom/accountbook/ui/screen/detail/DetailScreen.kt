package com.seom.accountbook.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.model.category.Category
import com.seom.accountbook.model.method.Method
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.screen.post.PostBody
import com.seom.accountbook.ui.screen.post.PostTopTab
import com.seom.accountbook.ui.theme.ColorPalette
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    categoryId: String? = null,
    onBackButtonPressed: () -> Unit
) {
    val categoryName = "생활"

    Scaffold(
        topBar = {
            OneButtonAppBar(title = categoryName) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.clickable { onBackButtonPressed() })
            }
        }
    ) {

        Column {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )
        }
    }
}