package com.seom.accountbook.ui.screen.post

import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seom.accountbook.AccountApp
import com.seom.accountbook.R
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.fullForamt
import com.seom.accountbook.util.ext.toMoney
import java.util.*

/**
 * 수입/지출 내역 화면 UI
 */

@Composable
fun PostScreen(
    postId: String? = null,
    onBackButtonPressed: () -> Unit
) {
    val postMode = postId.isNullOrBlank()
    var currentSelectedTab by remember { mutableStateOf(HistoryType.INCOME) }

    Scaffold(
        topBar = {
            OneButtonAppBar(title = if (postMode) "내역 등록" else "내역 수정") {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onBackButtonPressed()
                    })
            }
        }
    ) {
        Column {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )
            PostTopTab(
                currentSelectedTab = currentSelectedTab,
                onTabSelected = { if (postMode) currentSelectedTab = it },
                modifier = Modifier.padding(16.dp)
            )
            PostBody(
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

// TODO 재활용할 수 있도록 수정하자
@Composable
fun PostTopTab(
    currentSelectedTab: HistoryType,
    onTabSelected: (HistoryType) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            // 수입 tab
            HistoryType.values().forEach {
                HistoryTypeItm(
                    type = it,
                    selected = it == currentSelectedTab,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(it) })
            }
        }
    }
}

@Composable
fun HistoryTypeItm(
    type: HistoryType,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                if (selected) ColorPalette.Purple
                else ColorPalette.LightPurple
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = type.title),
            style = MaterialTheme.typography.subtitle1,
            color = ColorPalette.White,
            modifier = Modifier.padding(
                start = 5.dp
            )
        )
    }
}

@Composable
fun PostBody(
    modifier: Modifier = Modifier
) {
    var date by remember { mutableStateOf(Calendar.getInstance()) }
    var money by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            AccountInputField(title = "일자") { DateInput(selectedDate = date) }
            AccountInputField(title = "금액") {
                MoneyInput(
                    money = money,
                    onValueChange = { it?.let { money = it } })
            }
            AccountInputField(title = "결제 수단") {}
            AccountInputField(title = "분류") {}
            AccountInputField(title = "내용") {}
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = ColorPalette.Yellow),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "등록하기",
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight(700),
                    color = ColorPalette.White
                ),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
fun AccountInputField(
    title: String,
    input: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight(500),
            color = ColorPalette.Purple,
            modifier = Modifier.weight(2f)
        )
        Surface(
            modifier = Modifier
                .weight(8f)
                .padding(start = 8.dp),
            color = Color.Transparent
        ) {
            input()
        }
    }
    Divider(
        color = ColorPalette.Purple40,
        thickness = 1.dp
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun DateInput(
    selectedDate: Calendar
) {
    Text(
        text = selectedDate.fullForamt(),
        style = MaterialTheme.typography.caption,
        color = ColorPalette.Purple
    )
}

@Composable
fun MoneyInput(
    money: Int,
    onValueChange: (Int?) -> Unit
) {
    BasicTextField(
        value = money.toMoney(),
        onValueChange = {
            onValueChange(
                if (it.isNullOrBlank()) 0
                else it.replace(",", "").toIntOrNull()
            )
        },
        textStyle = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple),
    )
}