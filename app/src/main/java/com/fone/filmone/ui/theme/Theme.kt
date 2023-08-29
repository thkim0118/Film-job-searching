package com.fone.filmone.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
    background = FColor.White,
    onBackground = FColor.White
)

private val LightColorPalette = lightColors(
    primary = FColor.Red300,
    secondary = FColor.Violet500,
)

@Composable
fun FilmOneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = FColor.Transparent.toArgb()
        window.navigationBarColor = FColor.Transparent.toArgb()

        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = darkTheme
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightNavigationBars = darkTheme
    }

    MaterialTheme(
        colors = LightColorPalette,
        shapes = Shapes,
        content = content
    )
}
