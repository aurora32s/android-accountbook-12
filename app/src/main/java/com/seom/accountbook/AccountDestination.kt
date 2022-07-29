package com.seom.accountbook

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AccountDestination {
    val icon: Int // icon drawable id
    val route: String // navigation destination path
    val title: String // navigation title
    val group: String // bottom navigation group
}

object History : AccountDestination {
    override val icon = R.drawable.ic_history
    override val route = "history"
    override val title = "내역"
    override val group = "history"
}

object Post : AccountDestination {
    override val icon = R.drawable.ic_settings
    override val route = "post"
    override val title = "설정"
    override val group = "history"
    const val postIdArg = "post_id"
    val routeWithArgs = "${route}/{${postIdArg}}"
    val arguments = listOf(
        navArgument(postIdArg){ type = NavType.StringType }
    )
}

object Calendar : AccountDestination {
    override val icon = R.drawable.ic_calendar
    override val route = "calendar"
    override val title = "달력"
    override val group = "calendar"
}

object Graph : AccountDestination {
    override val icon = R.drawable.ic_graph
    override val route = "graph"
    override val title = "통계"
    override val group = "graph"
}

object Setting : AccountDestination {
    override val icon = R.drawable.ic_settings
    override val route = "setting"
    override val title = "설정"
    override val group = "setting"
}

val accountBottomTabScreens = listOf(History, Calendar, Graph, Setting)