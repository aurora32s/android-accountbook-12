package com.seom.accountbook.ui.screen.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.seom.accountbook.R
import com.seom.accountbook.ui.AccountViewModel
import com.seom.accountbook.util.route.AccountTopDestination

object GraphDestination : AccountTopDestination {
    override val icon = R.drawable.ic_graph
    override val route = "graph"
    override val group = "graph"
    override val title = "통계"
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.graph(
    mainViewModel: AccountViewModel,
    navigate: (String, String) -> Unit
) {
    composable(route = GraphDestination.route) {
        GraphScreen(mainViewModel = mainViewModel, navigate = navigate)
    }
}