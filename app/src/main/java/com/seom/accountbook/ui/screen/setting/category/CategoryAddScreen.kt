package com.seom.accountbook.ui.screen.setting.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.screen.setting.method.Input
import com.seom.accountbook.ui.screen.setting.method.InputField
import com.seom.accountbook.ui.theme.ColorPalette
import kotlin.math.min

val outcomeColor = listOf(
    Color(0xFF4A6CC3),
    Color(0xFF2E86C7),
    Color(0xFF4CA1DE),
    Color(0xFF48C2E9),
    Color(0xFF6ED5EB),
    Color(0xFF9FE7C8),
    Color(0xFF94D3CC),
    Color(0xFF4CB8B8),
    Color(0xFF40B98D),
    Color(0xFF2FA488),
    Color(0xFF625EBA),
    Color(0xFF817DCE),
    Color(0xFF9B7DCE),
    Color(0xFFB391EB),
    Color(0xFFD092E2),
    Color(0xFFF1B4EF),
    Color(0xFFF4AEE1),
    Color(0xFFF396B8),
    Color(0xFFDC5D7B),
    Color(0xFFCB588F)
)

val incomeColor = listOf(
    Color(0xFF9BD182),
    Color(0xFFA3CB7A),
    Color(0xFFB5CC7A),
    Color(0xFFCCD67A),
    Color(0xFFEAE07C),
    Color(0xFFEDCF65),
    Color(0xFFEBC374),
    Color(0xFFE1AD60),
    Color(0xFFE29C4D),
    Color(0xFFE39145)
)

@Composable
fun CategoryAddScreen(
    categoryId: String? = null,
    categoryType: HistoryType,
    onBackButtonPressed: () -> Unit
) {
    val title = if (categoryType == HistoryType.INCOME) "수입" else "지출"
    val modeTitle = if (categoryId.isNullOrBlank()) "추가하기" else "수정하기"
    val colorList = if (categoryType == HistoryType.INCOME) incomeColor else outcomeColor

    var name by remember { mutableStateOf("") }
    val selectedIndex by remember { mutableStateOf(0) }

    Scaffold(topBar = {
        OneButtonAppBar(title = "$title 카테고리 $modeTitle") {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.clickable { onBackButtonPressed() })
        }
    }) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )
            Column {
                InputField(title = "이름") {
                    Input(content = name, onValueChange = { name = it })
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

                ColorSelector(colors = colorList, perLine = 10, selectedIndex = selectedIndex)
                Spacer(modifier = Modifier.height(5.dp))
                Divider(color = ColorPalette.LightPurple, thickness = 1.dp)
            }

            Button(
                onClick = { onBackButtonPressed() },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorPalette.Yellow,
                    disabledBackgroundColor = ColorPalette.Yellow50
                ),
                enabled = name.isNullOrBlank().not()
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
    colors: List<Color>,
    perLine: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier
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
                    Spacer(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(if (row * perLine + column == selectedIndex) 0.dp else 4.dp)
                            .background(colors[row * perLine + column])
                    )
                }
            }
        }
    }
}