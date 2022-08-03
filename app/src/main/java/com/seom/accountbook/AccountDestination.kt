package com.seom.accountbook

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AccountDestination {
    val icon: Int // icon drawable id
    val route: String // navigation destination path
    val title: String // navigation title
    val group: String // bottom navigation group
}

object History : AccountDestination {
    override val icon = R.drawable.ic_history
    override val route = "history"
    override val title = "내역"
    override val group = "history"
}

object PostDestination : AccountDestination {
    override val icon = R.drawable.ic_history
    override val route = "post"
    override val title = "작성"
    override val group = "history"
    const val postIdArg = "post_id"
    val routeWithArgs = "${route}/{${postIdArg}}"
    val arguments = listOf(
        navArgument(postIdArg) { type = NavType.StringType }
    )
}

object Calendar : AccountDestination {
    override val icon = R.drawable.ic_calendar
    override val route = "calendar"
    override val title = "달력"
    override val group = "calendar"
}

object Graph : AccountDestination {
    override val icon = R.drawable.ic_graph
    override val route = "graph"
    override val title = "통계"
    override val group = "graph"
}

object DetailDestination : AccountDestination {
    override val icon = R.drawable.ic_graph
    override val route = "detail"
    override val title = "상세"
    override val group = "graph"
    const val categoryIdArgs = "category_id"
    const val yearArgs = "year"
    const val monthArgs = "month"
    val routeWithArgs = "${route}/{${categoryIdArgs}}/{${yearArgs}}/{${monthArgs}}"
    val arguments = listOf(
        navArgument(categoryIdArgs) { type = NavType.StringType },
        navArgument(yearArgs) { type = NavType.IntType },
        navArgument(monthArgs) { type = NavType.IntType }
    )
}

object Setting : AccountDestination {
    override val icon = R.drawable.ic_settings
    override val route = "setting"
    override val title = "설정"
    override val group = "setting"
}

object MethodDestination : AccountDestination {
    override val icon = R.drawable.ic_settings
    override val route = "method"
    override val title = "결제수단"
    override val group = "setting"

    const val methodIdArgs = "method_id"
    const val methodTypeArgs = "method_type"
    val routeWithArgs = "${route}/{${methodTypeArgs}}"
    val routeWithAllArgs = "${route}/{${methodTypeArgs}}/{${methodIdArgs}}"
    val allArguments = listOf(
        navArgument(methodIdArgs) { type = NavType.StringType },
        navArgument(methodTypeArgs) { type = NavType.IntType }
    )
    val arguments = listOf(
        navArgument(methodTypeArgs) { type = NavType.IntType }
    )
}

object CategoryDestination : AccountDestination {
    override val icon = R.drawable.ic_settings
    override val route = "category"
    override val title = "카테고리"
    override val group = "setting"

    const val categoryIdArgs = "category_id"
    const val categoryTypeArgs = "category_type"
    val routeWithArgs = "${route}/{${categoryTypeArgs}}"
    val routeWithAllArgs = "${route}/{${categoryTypeArgs}}/{${categoryIdArgs}}"
    val allArguments = listOf(
        navArgument(categoryIdArgs) { type = NavType.StringType },
        navArgument(categoryTypeArgs) { type = NavType.StringType }
    )
    val arguments = listOf(
        navArgument(categoryTypeArgs) { type = NavType.StringType }
    )
}

val allScreens = listOf(History, PostDestination, Calendar, Graph, DetailDestination, Setting, MethodDestination, CategoryDestination)
val accountBottomTabScreens = listOf(History, Calendar, Graph, Setting)