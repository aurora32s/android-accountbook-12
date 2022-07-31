package com.seom.accountbook.ui.components.datesheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * 날짜 선택 bottom sheet 의 container
 */
@Composable
fun BasicDateBottomSheet(
    modifier: Modifier = Modifier,
    onClickCloseBtn: () -> Unit,
    onClickConfirmBtn: () -> Unit,
    child: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 20.dp, bottom = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "날짜 선택",
                style = MaterialTheme.typography.body2,
                color = ColorPalette.Purple,
                fontWeight = FontWeight(700)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorPalette.Purple),
                modifier = Modifier.clickable {
                    onClickCloseBtn()
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            child()
        }
        Button(
            onClick = { onClickConfirmBtn() },
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = ColorPalette.Yellow)
        ) {
            Text(
                text = "완료",
                style = MaterialTheme.typography.caption,
                color = ColorPalette.White
            )
        }
    }
}