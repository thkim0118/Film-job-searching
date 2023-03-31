package com.fone.filmone.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.ui.FOneDestinations

@Composable
fun SignUpNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = FOneDestinations.SignUp.SignUpFirst.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(FOneDestinations.SignUp.SignUpFirst.route) {
            SignUpFirstScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(FOneDestinations.SignUp.SignUpSecond.route) {
            SignUpSecondScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(FOneDestinations.SignUp.SignUpThird.route) {
            SignUpThirdScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(FOneDestinations.SignUp.SignUpComplete.route) {
            SignUpCompleteScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }
}