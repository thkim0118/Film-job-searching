package com.fone.filmone.ui.signup

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fone.filmone.core.LogUtil
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.signup.model.SignUpFirstVo
import com.fone.filmone.ui.signup.screen.SignUpCompleteScreen
import com.fone.filmone.ui.signup.screen.SignUpFirstScreen
import com.fone.filmone.ui.signup.screen.SignUpSecondScreen
import com.fone.filmone.ui.signup.screen.SignUpThirdScreen

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
        composable(
            route = FOneDestinations.SignUp.SignUpSecond.routeWithArgs,
            arguments = FOneDestinations.SignUp.SignUpSecond.arguments
        ) { navBackStackEntry ->
            val signUpFirstVo =
                navBackStackEntry.arguments?.getString(FOneDestinations.SignUp.SignUpSecond.argSignUpFirstModel)
                    ?: return@composable
            LogUtil.d("${SignUpFirstVo.fromJson(signUpFirstVo)}")

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