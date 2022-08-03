package com.seom.accountbook.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.seom.accountbook.ui.screen.setting.SettingScreen
import com.seom.accountbook.ui.screen.setting.category.CategoryAddScreen
import com.seom.accountbook.ui.screen.setting.method.MethodAddScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountNavigationHost(
    viewModel: AccountViewModel,
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
                mainViewModel = viewModel,
                onDateChange = viewModel::setDate,
                viewModel = viewModel(),
                onPushNavigate = { route, argument ->
                    navController.navigateSingleTop(route, argument)
                }
            )
        }
        composable(route = Calendar.route) {
            CalendarScreen(
                mainViewModel = viewModel,
                onDateChange = viewModel::setDate,
                viewModel = viewModel()
            )
        }
        composable(route = Graph.route) {
            GraphScreen(
                mainViewModel = viewModel,
                onDateChange = viewModel::setDate,
                viewModel = viewModel(),
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
            route = PostDestination.routeWithArgs,
            arguments = PostDestination.arguments
        ) { navBackStackEntry ->
            val postId = navBackStackEntry.arguments?.getString(PostDestination.postIdArg)
            PostScreen(
                postId = postId,
                viewModel = viewModel(),
                onPushNavigation = { route, argument ->
                    navController.navigateSingleTop(route, argument)
                },
                onBackButtonPressed = { navController.popBackStack() }
            )
        }
        composable(route = PostDestination.route) {
            PostScreen(
                viewModel = viewModel(),
                onPushNavigation = { route, argument ->
                    navController.navigateSingleTop(route, argument)
                },
                onBackButtonPressed = { navController.popBackStack() }
            )
        }

        composable(
            route = DetailDestination.routeWithArgs,
            arguments = DetailDestination.arguments
        ) { navBackStackEntry ->
            val year = navBackStackEntry.arguments?.getInt(DetailDestination.yearArgs)
            val month = navBackStackEntry.arguments?.getInt(DetailDestination.monthArgs)
            val categoryId =
                navBackStackEntry.arguments?.getString(DetailDestination.categoryIdArgs)

            if (year != null && month != null && categoryId != null) {
                DetailScreen(
                    year = year,
                    month = month,
                    categoryId = categoryId,
                    viewModel = viewModel(),
                    onBackButtonPressed = { navController.popBackStack() }
                )
            }
        }
        // 결제/입금 수단 새로 추가
        composable(
            route = MethodDestination.routeWithArgs,
            arguments = MethodDestination.arguments
        ) { navBackStackEntry ->
            val methodType =
                navBackStackEntry.arguments?.getInt(MethodDestination.methodTypeArgs)
            MethodAddScreen(
                methodType = HistoryType.getHistoryType(methodType ?: 0),
                viewModel = viewModel()
            ) {
                navController.popBackStack()
            }
        }
        // 결제/입금 수단 변경
        composable(
            route = MethodDestination.routeWithAllArgs,
            arguments = MethodDestination.allArguments
        ) { navBackStackEntry ->
            val methodId = navBackStackEntry.arguments?.getString(MethodDestination.methodIdArgs)
            val methodType =
                navBackStackEntry.arguments?.getInt(MethodDestination.methodTypeArgs)
            MethodAddScreen(
                methodId = methodId,
                methodType = HistoryType.getHistoryType(methodType ?: 0),
                viewModel = viewModel()
            ) {
                navController.popBackStack()
            }
        }
        // 카테고리 새로 추가
        composable(
            route = CategoryDestination.routeWithArgs,
            arguments = CategoryDestination.arguments
        ) { navBackStackEntry ->
            val categoryType =
                navBackStackEntry.arguments?.getString(CategoryDestination.categoryTypeArgs)
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
            route = CategoryDestination.routeWithAllArgs,
            arguments = CategoryDestination.allArguments
        ) { navBackStackEntry ->
            val categoryId =
                navBackStackEntry.arguments?.getString(CategoryDestination.categoryIdArgs)
            val categoryType =
                navBackStackEntry.arguments?.getString(CategoryDestination.categoryTypeArgs)
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
    navigate("$route${if (argument.isBlank()) "" else "/${argument}"}") {
        // back button 클릭 시에는 이전 tab 으로 이도할 수 있도록 하기 위해 popUpTo는 지정하지 않음
        launchSingleTop = true
    }
}