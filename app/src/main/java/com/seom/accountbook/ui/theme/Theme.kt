package com.seom.accountbook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = ColorPalette.Purple,
    primaryVariant = ColorPalette.LightPurple,
    secondary = ColorPalette.Purple40
)

private val LightColorPalette = lightColors(
    primary = ColorPalette.Purple,
    primaryVariant = ColorPalette.LightPurple,
    secondary = ColorPalette.Purple40,
    onPrimary = ColorPalette.White,
    background = ColorPalette.OffWhite
)

@Composable
fun AccountBookTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = colors.background
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}