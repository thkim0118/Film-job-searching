package com.fone.filmone.ui.signup.screen

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fone.filmone.ui.navigation.FOneDestinations

fun NavGraphBuilder.nestedSignUpScreenGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    navigation(
        startDestination = FOneDestinations.SignUp.SignUpFirst.route,
        route = FOneDestinations.SignUp.route
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