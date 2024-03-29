package com.fone.filmone.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fone.filmone.ui.navigation.FOneNavGraph
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun FOneApp() {
    InitSystemBarColor()

    FilmOneTheme {
        Scaffold(
            modifier = Modifier,
        ) {
            FOneNavGraph(
                modifier = Modifier.padding(it),
            )
        }
    }
}

@Composable
private fun InitSystemBarColor() {
    val systemUiController = rememberSystemUiController()

    with(systemUiController) {
        setStatusBarColor(color = FColor.White)
        setNavigationBarColor(color = FColor.White)
    }
}
