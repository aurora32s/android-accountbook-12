package com.seom.accountbook.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seom.accountbook.Calendar
import com.seom.accountbook.Graph
import com.seom.accountbook.History
import com.seom.accountbook.Setting
import com.seom.accountbook.ui.screen.calendar.CalendarScreen
import com.seom.accountbook.ui.screen.graph.GraphScreen
import com.seom.accountbook.ui.screen.history.HistoryScreen
import com.seom.accountbook.ui.screen.setting.SettingScreen

@Composable
fun AccountNavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = History.route,
        modifier = modifier
    ) {
        composable(route = History.route) {
            HistoryScreen()
        }
        composable(route = Calendar.route) {
            CalendarScreen()
        }
        composable(route = Graph.route) {
            GraphScreen()
        }
        composable(route = Setting.route) {
            SettingScreen()
        }
    }
}

fun NavController.navigateSingleTop(route: String) {
    navigate(route) {
        // back button 클릭 시에는 이전 tab 으로 이도할 수 있도록 하기 위해 popUpTo는 지정하지 않음
        launchSingleTop = true
    }
}