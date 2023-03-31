package com.fone.filmone.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.ui.login.LoginScreen
import com.fone.filmone.ui.signup.SignUpScreen
import com.fone.filmone.ui.splash.SplashScreen

@Composable
fun FOneNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = FOneDestinations.Splash.route
) {
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

        }
        composable(FOneDestinations.SignUp.route) {
            SignUpScreen()
        }
        composable(FOneDestinations.Home.route) {

        }
    }
}