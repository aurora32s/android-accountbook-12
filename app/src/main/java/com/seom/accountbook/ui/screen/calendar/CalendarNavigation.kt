package com.seom.accountbook.ui.screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.seom.accountbook.R
import com.seom.accountbook.ui.AccountViewModel
import com.seom.accountbook.util.route.AccountTopDestination

object CalendarDestination : AccountTopDestination {
    override val icon = R.drawable.ic_calendar
    override val route = "calendar"
    override val group = "calendar"
    override val title = "달력"
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.calendar(
    mainViewModel: AccountViewModel
) {
    composable(route = CalendarDestination.route) {
        CalendarScreen(mainViewModel = mainViewModel)
    }
}