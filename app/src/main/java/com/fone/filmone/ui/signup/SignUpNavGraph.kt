package com.fone.filmone.ui.signup

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.signup.model.SignUpVo
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
            val signUpVo =
                navBackStackEntry.arguments?.getString(FOneDestinations.SignUp.argSignUpVo)
                    ?: return@composable

            SignUpSecondScreen(
                modifier = modifier,
                navController = navController,
                signUpVo = SignUpVo.fromJson(signUpVo)
            )
        }
        composable(
            route = FOneDestinations.SignUp.SignUpThird.routeWithArgs,
            arguments = FOneDestinations.SignUp.SignUpThird.arguments
        ) { navBackStackEntry ->
            val signUpVo =
                navBackStackEntry.arguments?.getString(FOneDestinations.SignUp.argSignUpVo)
                    ?: return@composable

            SignUpThirdScreen(
                modifier = modifier,
                navController = navController,
                signUpVo = SignUpVo.fromJson(signUpVo)
            )
        }
        composable(
            route = FOneDestinations.SignUp.SignUpComplete.routeWithArgs,
            arguments = FOneDestinations.SignUp.SignUpComplete.arguments
        ) { navBackStackEntry ->
            val signUpVo =
                navBackStackEntry.arguments?.getString(FOneDestinations.SignUp.argSignUpVo)
                    ?: return@composable

            SignUpCompleteScreen(
                modifier = modifier,
                signUpVo = SignUpVo.fromJson(signUpVo)
            )
        }
    }
}