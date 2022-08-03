package com.seom.accountbook.ui.screen.method

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.util.route.AccountDestination


object MethodDestination : AccountDestination {
    override val route = "method"
    override val group = "setting"

    const val methodIdArgs = "method_id"
    const val methodTypeArgs = "method_type"

    val routeWithArgs = "$route/{$methodTypeArgs}"
    val routeWithAllArgs = "$route/{$methodTypeArgs}/{$methodIdArgs}"

    val allArguments = listOf(
        navArgument(methodIdArgs) { type = NavType.LongType },
        navArgument(methodTypeArgs) { type = NavType.IntType }
    )
    val arguments = listOf(
        navArgument(methodTypeArgs) { type = NavType.IntType }
    )
}

fun NavGraphBuilder.method(
    onBackPressed: () -> Unit
) {
    composable(
        route = MethodDestination.routeWithArgs,
        arguments = MethodDestination.arguments
    ) {
        val methodType = it.arguments?.getInt(MethodDestination.methodTypeArgs)
        MethodAddScreen(
            methodType = HistoryType.getHistoryType(methodType ?: 0),
            onBackPressed = onBackPressed
        )
    }

    composable(
        route = MethodDestination.routeWithAllArgs,
        arguments = MethodDestination.allArguments
    ) {
        val methodId = it.arguments?.getLong(MethodDestination.methodIdArgs)
        val methodType = it.arguments?.getInt(MethodDestination.methodTypeArgs)

        MethodAddScreen(
            methodId = methodId,
            methodType = HistoryType.getHistoryType(methodType ?: 0),
            onBackPressed = onBackPressed
        )
    }
}