package com.fone.filmone.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fone.filmone.R
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        delay(2000L)
        when (uiState) {
            SplashUiState.AutoLoginUser -> {
                FOneNavigator.navigateTo(
                    navDestinationState = NavDestinationState(
                        route = FOneDestinations.Main.route,
                        isPopAll = true
                    )
                )
            }
            SplashUiState.NotLoginUser -> {
                FOneNavigator.navigateTo(
                    navDestinationState = NavDestinationState(
                        route = FOneDestinations.Login.route,
                        isPopAll = true
                    )
                )
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = FColor.Black)
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            imageVector = ImageVector.vectorResource(id = R.drawable.splash_fone_logo),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    FilmOneTheme {
        SplashScreen()
    }
}
