package com.seom.accountbook.util.route

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seom.accountbook.*
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.AccountViewModel
import com.seom.accountbook.ui.screen.calendar.calendar
import com.seom.accountbook.ui.screen.detail.DetailScreen
import com.seom.accountbook.ui.screen.detail.detail
import com.seom.accountbook.ui.screen.graph.graph
import com.seom.accountbook.ui.screen.history.HistoryDestination
import com.seom.accountbook.ui.screen.history.history
import com.seom.accountbook.ui.screen.post.post
import com.seom.accountbook.ui.screen.setting.SettingScreen
import com.seom.accountbook.ui.screen.category.CategoryAddScreen
import com.seom.accountbook.ui.screen.category.category
import com.seom.accountbook.ui.screen.method.MethodAddScreen
import com.seom.accountbook.ui.screen.method.method
import com.seom.accountbook.ui.screen.setting.setting

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountNavigationHost(
    modifier: Modifier = Modifier,
    mainViewModel: AccountViewModel = hiltViewModel(),
    navController: NavHostController,
    startDestination: String = HistoryDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        history( // 수입/지출 내역 리스트 화면
            mainViewModel = mainViewModel,
            navigate = navController::navigateSingleTop
        )
        post( // 수입/지출 내역 작성 화면
            onBackPressed = navController::popBackStack,
            navigate = navController::navigateSingleTop
        )
        calendar( // 달력 화면
            mainViewModel = mainViewModel
        )
        graph( // 통계 화면
            mainViewModel = mainViewModel,
            navigate = navController::navigateSingleTop
        )
        detail( // 카테고리별 6개월 이내 상세 지출 내역
            onBackPressed = navController::popBackStack
        )
        // 설정 화면
        setting(navigate = navController::navigateSingleTop)
        method( // 결제수단/입금계좌 등록/수정 화면
            onBackPressed = navController::popBackStack
        )
        category( // 수입/지출 카테고리 등록/수정 화면
            onBackPressed = navController::popBackStack
        )
    }
}

fun NavController.navigateSingleTop(route: String, argument: String = "") {
    navigate("$route${if (argument.isBlank()) "" else "/${argument}"}") {
        // back button 클릭 시에는 이전 tab 으로 이도할 수 있도록 하기 위해 popUpTo는 지정하지 않음
        launchSingleTop = true
    }
}