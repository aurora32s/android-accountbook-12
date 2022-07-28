package com.seom.accountbook.ui.screen.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.theme.ColorPalette

@Preview(
    name = "HistoryScreen"
)
@Composable
fun HistoryScreen() {
    var currentSelectedTab by remember { mutableStateOf(HistoryType.INCOME) }

    HistoryTopTab(
        currentSelectedTab = currentSelectedTab,
        onTabSelected = { currentSelectedTab = it },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun HistoryTopTab(
    income: Int, // 수입 총금액
    outCome: Int, // 지출 총금액
    currentSelectedTab: HistoryType,
    onTabSelected: (HistoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        HistoryType.values().forEach { type ->
            Row(
            ) {
                if (type == currentSelectedTab) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(ColorPalette.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = ColorPalette.Purple),
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_checkbox),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = ColorPalette.White),
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    text = stringResource(id = "${type.title} ${income}원"),
                    style = MaterialTheme.typography.body2,
                    color = ColorPalette.White,
                    modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
                )
            }
        }
    }
}

@Composable
fun HistoryTypeItem(
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                if (selected) ColorPalette.Purple
                else ColorPalette.LightPurple
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp))
    }
}
