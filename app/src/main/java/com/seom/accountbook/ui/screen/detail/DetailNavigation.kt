package com.seom.accountbook.ui.screen.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seom.accountbook.util.route.AccountDestination

object DetailDestination : AccountDestination {
    override val route = "detail"
    override val group = "graph"

    const val categoryIdArgs = "category_id"
    const val yearArgs = "year"
    const val monthArgs = "month"

    val routeWithArgs = "${route}/{${categoryIdArgs}}/{${yearArgs}}/{${monthArgs}}"
    val arguments = listOf(
        navArgument(categoryIdArgs) { type = NavType.LongType },
        navArgument(yearArgs) { type = NavType.IntType },
        navArgument(monthArgs) { type = NavType.IntType }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.detail(
    onBackPressed: () -> Unit
) {
    composable(
        route = DetailDestination.routeWithArgs,
        arguments = DetailDestination.arguments
    ) {
        val year = it.arguments?.getInt(DetailDestination.yearArgs)
        val month = it.arguments?.getInt(DetailDestination.monthArgs)
        val categoryId = it.arguments?.getLong(DetailDestination.categoryIdArgs)

        if (year != null && month != null && categoryId != null) {
            DetailScreen(
                year = year,
                month = month,
                categoryId = categoryId,
                onBackPressed = onBackPressed
            )
        }
    }
}
