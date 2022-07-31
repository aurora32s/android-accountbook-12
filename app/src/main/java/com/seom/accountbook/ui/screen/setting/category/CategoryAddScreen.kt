package com.seom.accountbook.ui.screen.setting.category

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.seom.accountbook.R
import com.seom.accountbook.model.category.incomeColor
import com.seom.accountbook.model.category.outcomeColor
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.BackButtonAppBar
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.screen.setting.method.Input
import com.seom.accountbook.ui.screen.setting.method.InputField
import com.seom.accountbook.ui.theme.ColorPalette
import kotlin.math.min

@Composable
fun CategoryAddScreen(
    categoryId: String? = null,
    categoryType: HistoryType,
    viewModel: CategoryViewModel,
    onBackButtonPressed: () -> Unit
) {
    val observeData = viewModel.categoryUiState.collectAsState()
    when (observeData.value) {
        CategoryUiState.UnInitialized -> viewModel.fetchCategory(
            categoryId = categoryId?.toLong(),
            categoryType = categoryType
        )
        CategoryUiState.Loading -> {}
        CategoryUiState.Success.AddCategory -> onBackButtonPressed()
        CategoryUiState.Success.FetchCategory -> SettingBody(
            viewModel = viewModel,
            isModifyMode = categoryId.isNullOrBlank().not(),
            categoryType = categoryType
        ) {

        }
        is CategoryUiState.Error -> {}
    }


}

@Composable
fun SettingBody(
    viewModel: CategoryViewModel,
    isModifyMode: Boolean,
    categoryType: HistoryType,
    onBackButtonPressed: ()->Unit
) {
    val title = if (categoryType == HistoryType.INCOME) "수입" else "지출"
    val modeTitle = if (isModifyMode) "추가하기" else "수정하기"

    val name = viewModel.name.collectAsState()
    val color = viewModel.color.collectAsState()

    Scaffold(topBar = {
        BackButtonAppBar(
            title = "$title 카테고리 $modeTitle",
            onClickBackBtn = onBackButtonPressed
        )
    }) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                InputField(title = "이름") {
                    Input(content = name.value, onValueChange = viewModel::setName)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    Text(
                        text = "색상",
                        style = MaterialTheme.typography.body2.copy(color = ColorPalette.LightPurple),
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Divider(color = ColorPalette.Purple40, thickness = 1.dp)
                }

                ColorSelector(
                    colors = viewModel.colorList,
                    perLine = 10,
                    selectedColor = color.value,
                    onSelectItem = viewModel::setColor
                )
                Spacer(modifier = Modifier.height(5.dp))
                Divider(color = ColorPalette.LightPurple, thickness = 1.dp)
            }

            Button(
                onClick = viewModel::addCategory,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorPalette.Yellow,
                    disabledBackgroundColor = ColorPalette.Yellow50
                ),
                enabled = name.value.isBlank().not()
            ) {
                Text(
                    text = "등록하기",
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight(700),
                        color = ColorPalette.White
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .background(Color.Transparent),
                )
            }
        }
    }
}

@Composable
fun ColorSelector(
    colors: List<Long>,
    perLine: Int,
    selectedColor: Long,
    modifier: Modifier = Modifier,
    onSelectItem: (Long) -> Unit
) {
    var rowNum = colors.size / perLine
    if (colors.size % perLine != 0)
        rowNum++

    Column(modifier.padding(16.dp)) {
        (0 until rowNum).forEachIndexed { _, row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                (0 until min(perLine, colors.size)).forEachIndexed { _, column ->
                    val color = colors[row * perLine + column]
                    val paddingAnimation by animateDpAsState(targetValue = if (color == selectedColor) 0.dp else 4.dp)
                    Spacer(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(paddingAnimation)
                            .background(Color(color))
                            .clickable { onSelectItem(color) }
                    )
                }
            }
        }
    }
}