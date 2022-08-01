package com.seom.accountbook.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seom.accountbook.*
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.screen.calendar.CalendarScreen
import com.seom.accountbook.ui.screen.detail.DetailScreen
import com.seom.accountbook.ui.screen.graph.GraphScreen
import com.seom.accountbook.ui.screen.history.HistoryScreen
import com.seom.accountbook.ui.screen.post.PostScreen
import com.seom.accountbook.ui.screen.post.PostViewModel
import com.seom.accountbook.ui.screen.setting.SettingScreen
import com.seom.accountbook.ui.screen.setting.category.CategoryAddScreen
import com.seom.accountbook.ui.screen.setting.method.MethodAddScreen

@RequiresApi(Build.VERSION_CODES.O)
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
            HistoryScreen(
                viewModel = viewModel(),
                onPushNavigate = { route, argument ->
                    navController.navigateSingleTop(route, argument)
                }
            )
        }
        composable(route = Calendar.route) {
            CalendarScreen(
                onPushNavigate = { route, argument ->
                    navController.navigateSingleTop(route, argument)
                }
            )
        }
        composable(route = Graph.route) {
            GraphScreen(
                onPushNavigate = { route, argument ->
                    navController.navigateSingleTop(route, argument)
                }
            )
        }
        composable(route = Setting.route) {
            SettingScreen(
                viewModel = viewModel()
            ) { route, args ->
                navController.navigateSingleTop(route, args)
            }
        }
        composable(
            route = Post.routeWithArgs,
            arguments = Post.arguments
        ) { navBackStackEntry ->
            val postId = navBackStackEntry.arguments?.getString(Post.postIdArg)
            PostScreen(
                postId = postId,
                viewModel = viewModel(),
                onBackButtonPressed = { navController.popBackStack() }
            )
        }
        composable(route = Post.route) {
            PostScreen(
                viewModel = viewModel(),
                onBackButtonPressed = { navController.popBackStack() }
            )
        }

        composable(
            route = Detail.routeWithArgs,
            arguments = Detail.arguments
        ) { navBackStackEntry ->
            val categoryId = navBackStackEntry.arguments?.getString(Detail.categoryIdArgs)
            DetailScreen(
                categoryId = categoryId,
                onBackButtonPressed = { navController.popBackStack() }
            )
        }
        // 결제 수단 새로 추가
        composable(
            route = Method.route
        ) {
            MethodAddScreen(
                viewModel = viewModel()
            ) {
                navController.popBackStack()
            }
        }
        // 결제 수단 변경
        composable(
            route = Method.routeWithArgs,
            arguments = Method.arguments
        ) { navBackStackEntry ->
            val methodId = navBackStackEntry.arguments?.getString(Method.methodIdArgs)
            MethodAddScreen(
                methodId = methodId,
                viewModel = viewModel()
            ) {
                navController.popBackStack()
            }
        }
        // 카테고리 새로 추가
        composable(
            route = Category.routeWithArgs,
            arguments = Category.arguments
        ) { navBackStackEntry ->
            val categoryType = navBackStackEntry.arguments?.getString(Category.categoryTypeArgs)
            CategoryAddScreen(
                null,
                HistoryType.getHistoryType(categoryType?.toInt() ?: 0),
                viewModel = viewModel()
            ) {
                navController.popBackStack()
            }
        }
        // 카테고리 변경
        composable(
            route = Category.routeWithAllArgs,
            arguments = Category.allArguments
        ) { navBackStackEntry ->
            val categoryId = navBackStackEntry.arguments?.getString(Category.categoryIdArgs)
            val categoryType = navBackStackEntry.arguments?.getString(Category.categoryTypeArgs)
            CategoryAddScreen(
                categoryId,
                HistoryType.getHistoryType(categoryType?.toInt() ?: 0),
                viewModel = viewModel()
            ) {
                navController.popBackStack()
            }
        }
    }
}

fun NavController.navigateSingleTop(route: String, argument: String = "") {
    navigate("$route${if (argument.isNullOrBlank()) "" else "/${argument}"}") {
        // back button 클릭 시에는 이전 tab 으로 이도할 수 있도록 하기 위해 popUpTo는 지정하지 않음
        launchSingleTop = true
    }
}