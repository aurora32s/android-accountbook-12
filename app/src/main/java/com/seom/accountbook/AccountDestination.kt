package com.seom.accountbook

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

interface AccountDestination {
    val icon: Int // icon drawable id
    val route: String // navigation destination title
}
