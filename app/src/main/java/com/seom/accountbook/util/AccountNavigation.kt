package com.seom.accountbook.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seom.accountbook.Calendar
import com.seom.accountbook.Graph
import com.seom.accountbook.History
import com.seom.accountbook.Setting

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

        }
        composable(route = Calendar.route) {

        }
        composable(route = Graph.route) {

        }
        composable(route = Setting.route) {

        }
    }
}