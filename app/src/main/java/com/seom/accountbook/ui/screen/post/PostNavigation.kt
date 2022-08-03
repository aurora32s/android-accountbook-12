package com.seom.accountbook.ui.screen.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seom.accountbook.util.route.AccountDestination

object PostDestination : AccountDestination {
    override val route = "post"
    override val group = "history"
    const val postIdArg = "post_id"

    val routeWithArgs = "${route}/{${postIdArg}}"
    val arguments = listOf(
        navArgument(postIdArg) { type = NavType.LongType }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.post(
    onBackPressed: () -> Unit,
    navigate: (String, String) -> Unit
) {
    composable(route = PostDestination.route) {
        PostScreen(
            onBackPressed = onBackPressed,
            navigate = navigate
        )
    }
    composable(
        route = PostDestination.routeWithArgs,
        arguments = PostDestination.arguments
    ) {
        val postId = it.arguments?.getLong(PostDestination.postIdArg)
        PostScreen(
            postId = postId,
            onBackPressed = onBackPressed,
            navigate = navigate
        )
    }
}