package com.seom.accountbook.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.seom.accountbook.AccountDestination

@Composable
fun AccountTabRow(
    allScreens: List<AccountDestination>,
    onTabSelected: (AccountDestination) -> Unit,
    currentScreen: AccountDestination
) {
    Surface(
        modifier = Modifier
            .height(tabHeight)
            .fillMaxWidth(),
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.selectableGroup(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            allScreens.forEach { screen ->
                AccountTab(
                    title = screen.title,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
fun AccountTab(
    title: String,
    @DrawableRes
    icon: Int,
    onSelected: () -> Unit,
    selected: Boolean
) {
    Column(
        modifier = Modifier
            .height(tabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false
                )
            )
            .clearAndSetSemantics { contentDescription = title },
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = icon), contentDescription = title)
        Text(text = title)
    }
}

private val tabHeight = 56.dp