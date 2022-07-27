package com.seom.accountbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.seom.accountbook.ui.components.AccountTabRow
import com.seom.accountbook.ui.theme.AccountBookTheme
import com.seom.accountbook.util.AccountNavigationHost

class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccountApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountApp() {
    val navController = rememberNavController()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        accountBottomTabScreens.find { it.route == currentDestination?.route } ?: History

    Scaffold(
        bottomBar = {
            AccountTabRow(
                allScreens = accountBottomTabScreens,
                onTabSelected = {

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