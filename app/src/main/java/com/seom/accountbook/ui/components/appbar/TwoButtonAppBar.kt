package com.seom.accountbook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seom.accountbook.R
import com.seom.accountbook.ui.components.image.IconImage
import com.seom.accountbook.ui.theme.ColorPalette

/**
 * 양쪽에 버튼이 두 개 있는 appbar
 */
@Composable
private fun TwoButtonAppBar(
    leftIcon: @Composable () -> Unit,
    rightIcon: @Composable () -> Unit,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        leftIcon()
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = ColorPalette.Purple
        )
        rightIcon()
    }
}

@Composable
fun BackButtonTwoAppBar(
    title: String,
    onClickBackBtn: () -> Unit,
    rightIcon: @Composable () -> Unit
) {
    TwoButtonAppBar(
        leftIcon = {
            IconImage(
                icon = R.drawable.ic_back,
                modifier = Modifier.clickable { onClickBackBtn() }
            )
        },
        rightIcon = rightIcon,
        title = title
    )
}