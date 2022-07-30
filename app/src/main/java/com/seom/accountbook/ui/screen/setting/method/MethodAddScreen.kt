package com.seom.accountbook.ui.screen.setting.method

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.ui.components.OneButtonAppBar
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun MethodAddScreen(
    methodId: String? = null,
    onBackButtonPressed: () -> Unit
) {
    val modeTitle = if (methodId.isNullOrBlank()) "추가하기" else "수정하기"
    var name by remember { mutableStateOf("") }

    Scaffold(topBar = {
        OneButtonAppBar(title = "결제 수단 $modeTitle") {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.clickable { onBackButtonPressed() })
        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Divider(
                color = ColorPalette.Purple,
                thickness = 1.dp
            )
            InputField(title = "이름") {
                Input(content = name, onValueChange = { name = it })
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
fun InputField(
    title: String,
    input: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)) {
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
}

@Composable
fun Input(
    content: String,
    onValueChange: (String) -> Unit
) {
    Box() {
        BasicTextField(
            value = content,
            onValueChange = { onValueChange(it) },
            textStyle = MaterialTheme.typography.caption.copy(color = ColorPalette.Purple),
        )
        if (content.isBlank()) {
            Text(
                text = "입력하세요.",
                style = MaterialTheme.typography.caption.copy(color = ColorPalette.LightPurple)
            )
        }
    }
}