package com.seom.accountbook

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

interface AccountDestination {
    val icon: Int // icon drawable id
    val route: String // navigation destination path
    val title: String // navigation title
}

object History : AccountDestination {
    override val icon = R.drawable.ic_history
    override val route = "history"
    override val title = "내역"
}

object Calendar : AccountDestination {
    override val icon = R.drawable.ic_calendar
    override val route = "calendar"
    override val title = "달력"
}

object Graph : AccountDestination {
    override val icon = R.drawable.ic_graph
    override val route = "graph"
    override val title = "통계"
}

object Setting : AccountDestination {
    override val icon = R.drawable.ic_settings
    override val route = "setting"
    override val title = "설정"
}

val accountBottomTabScreens = listOf(History, Calendar, Graph, Setting)