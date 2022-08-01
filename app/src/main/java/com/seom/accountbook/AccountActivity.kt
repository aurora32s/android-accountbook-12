package com.seom.accountbook

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.seom.accountbook.ui.components.AccountTabRow
import com.seom.accountbook.ui.theme.AccountBookTheme
import com.seom.accountbook.util.AccountNavigationHost
import com.seom.accountbook.util.navigateSingleTop

class AccountActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AccountApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AccountApp() {
    val navController = rememberNavController()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        allScreens.find { currentDestination?.route?.startsWith(it.route) ?: false } ?: History

    println(currentDestination?.route)
    AccountBookTheme() {
        Scaffold(
            bottomBar = {
                AccountTabRow(
                    allScreens = accountBottomTabScreens,
                    onTabSelected = { screen ->
                        navController.navigateSingleTop(screen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            AccountNavigationHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}