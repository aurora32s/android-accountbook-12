package com.seom.accountbook.util.route

import com.seom.accountbook.ui.screen.calendar.CalendarDestination
import com.seom.accountbook.ui.screen.detail.DetailDestination
import com.seom.accountbook.ui.screen.graph.GraphDestination
import com.seom.accountbook.ui.screen.history.HistoryDestination
import com.seom.accountbook.ui.screen.method.MethodDestination
import com.seom.accountbook.ui.screen.post.PostDestination
import com.seom.accountbook.ui.screen.setting.SettingDestination

interface AccountTopDestination : AccountDestination {
    val icon: Int // icon drawable id
    val title: String // navigation title
}

interface AccountDestination {
    val route: String
    val group: String
}

val allScreens = listOf(
    HistoryDestination,
    PostDestination,
    CalendarDestination,
    GraphDestination,
    DetailDestination,
    SettingDestination,
    MethodDestination,
    CalendarDestination
)
val accountBottomTabScreens = listOf(
    HistoryDestination,
    CalendarDestination,
    GraphDestination,
    SettingDestination
)