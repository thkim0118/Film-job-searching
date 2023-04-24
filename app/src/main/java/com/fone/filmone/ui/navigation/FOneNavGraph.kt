package com.fone.filmone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.ui.favorite.FavoriteScreen
import com.fone.filmone.ui.inquiry.InquiryScreen
import com.fone.filmone.ui.login.LoginScreen
import com.fone.filmone.ui.main.MainScreen
import com.fone.filmone.ui.myinfo.MyInfoScreen
import com.fone.filmone.ui.myregister.MyRegisterScreen
import com.fone.filmone.ui.scrap.ScrapScreen
import com.fone.filmone.ui.signup.signUpNavGraph
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
        FOneNavigator.routeFlow.onEach { navigationState ->
            navController.navigate(navigationState.route) {
                if (navigationState.isPopAll) {
                    popUpTo(0)
                }
            }
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(FOneDestinations.Splash.route) {
            SplashScreen()
        }
        composable(FOneDestinations.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(FOneDestinations.Inquiry.route) {
            InquiryScreen(navController = navController)
        }
        signUpNavGraph(navController = navController)
        composable(FOneDestinations.Main.route) {
            MainScreen(navController = navController)
        }
        composable(FOneDestinations.MyInfo.route) {
            MyInfoScreen(navController = navController)
        }
        composable(FOneDestinations.Scrap.route) {
            ScrapScreen(navController = navController)
        }
        composable(FOneDestinations.Favorite.route) {
            FavoriteScreen(navController = navController)
        }
        composable(FOneDestinations.MyRegister.route) {
            MyRegisterScreen(navController = navController)
        }
    }
}