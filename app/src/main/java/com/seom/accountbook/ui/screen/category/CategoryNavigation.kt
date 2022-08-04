package com.seom.accountbook.ui.screen.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.util.route.AccountDestination

object CategoryDestination : AccountDestination {
    override val route = "category"
    override val group = "setting"

    const val categoryIdArgs = "category_id"
    const val categoryTypeArgs = "category_type"

    val routeWithArgs = "${route}/{${categoryTypeArgs}}"
    val routeWithAllArgs = "${route}/{${categoryTypeArgs}}/{${categoryIdArgs}}"

    val allArguments = listOf(
        navArgument(categoryIdArgs) { type = NavType.LongType },
        navArgument(categoryTypeArgs) { type = NavType.IntType }
    )
    val arguments = listOf(
        navArgument(categoryTypeArgs) { type = NavType.IntType }
    )
}

fun NavGraphBuilder.category(
    onBackPressed: () -> Unit
) {
    composable(
        route = CategoryDestination.routeWithArgs,
        arguments = CategoryDestination.arguments
    ) {
        val categoryType = it.arguments?.getInt(CategoryDestination.categoryTypeArgs)
        CategoryAddScreen(
            categoryType = HistoryType.getHistoryType(categoryType ?: 0),
            onBackPressed = onBackPressed
        )
    }

    composable(
        route = CategoryDestination.routeWithAllArgs,
        arguments = CategoryDestination.allArguments
    ) {
        val categoryId = it.arguments?.getLong(CategoryDestination.categoryIdArgs)
        val categoryType = it.arguments?.getInt(CategoryDestination.categoryTypeArgs)

        CategoryAddScreen(
            categoryId = categoryId,
            categoryType = HistoryType.getHistoryType(categoryType ?: 0),
            onBackPressed = onBackPressed
        )
    }
}