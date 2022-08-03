package com.seom.accountbook.ui.screen.setting

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.seom.accountbook.R
import com.seom.accountbook.util.route.AccountTopDestination

object SettingDestination : AccountTopDestination {
    override val icon = R.drawable.ic_settings
    override val route = "setting"
    override val group = "setting"
    override val title = "설정"
}

fun NavGraphBuilder.setting(
    navigate: (String, String) -> Unit
) {
    composable(route = SettingDestination.route) {
        SettingScreen(
            navigate = navigate
        )
    }
}