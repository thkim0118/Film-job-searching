package com.fone.filmone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.ui.inquiry.InquiryScreen
import com.fone.filmone.ui.login.LoginScreen
import com.fone.filmone.ui.signup.signUpScreenComposable
import com.fone.filmone.ui.splash.SplashScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun FOneNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = FOneDestinations.Splash.route,
) {
    LaunchedEffect(key1 = "navigation") {
        FOneNavigator.destinationFlow.onEach {
            navController.navigate(it.route)
        }.launchIn(this)

        FOneNavigator.routeFlow.onEach { route ->
            navController.navigate(route)
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(FOneDestinations.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(FOneDestinations.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(FOneDestinations.Inquiry.route) {
            InquiryScreen(navController = navController)
        }
        signUpScreenComposable(navController = navController)
        composable(FOneDestinations.Home.route) {

        }
    }
}