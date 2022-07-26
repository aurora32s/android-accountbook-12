package com.seom.accountbook

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

interface AccountDestination {
    val icon: Int // icon drawable id
    val route: String // navigation destination title
}

object History : AccountDestination {
    override val icon = R.drawable.ic_history
    override val route = "history"
}

object Calendar : AccountDestination {
    override val icon = R.drawable.ic_calendar
    override val route = "calendar"
}

object Graph : AccountDestination {
    override val icon = R.drawable.ic_graph
    override val route = "graph"
}

object Setting : AccountDestination {
    override val icon = R.drawable.ic_settings
    override val route = "setting"
}

val accountBottomTabScreens = listOf(History, Calendar, Graph, Setting)