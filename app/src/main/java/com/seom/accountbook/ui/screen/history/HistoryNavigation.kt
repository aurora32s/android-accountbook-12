package com.seom.accountbook.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.seom.accountbook.R
import com.seom.accountbook.ui.AccountViewModel
import com.seom.accountbook.util.route.AccountTopDestination

object HistoryDestination : AccountTopDestination {
    override val icon = R.drawable.ic_history
    override val route = "history"
    override val group = "history"
    override val title = "내역"
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.history(
    mainViewModel: AccountViewModel,
    navigate: (String, String) -> Unit
) {
    composable(route = HistoryDestination.route) {
        HistoryScreen(
            mainViewModel = mainViewModel,
            navigate = navigate
        )
    }
}