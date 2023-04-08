package com.fone.filmone.ui.signup

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.signup.screen.SignUpCompleteScreen
import com.fone.filmone.ui.signup.screen.SignUpFirstScreen
import com.fone.filmone.ui.signup.screen.SignUpSecondScreen
import com.fone.filmone.ui.signup.screen.SignUpThirdScreen

fun NavGraphBuilder.signUpScreenComposable(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    composable(
        route = FOneDestinations.SignUpFirst.routeWithArgs,
        arguments = FOneDestinations.SignUpFirst.arguments
    ) { navBackStackEntry ->
        val signUpVo =
            navBackStackEntry.arguments?.getString(FOneDestinations.SignUpFirst.argSignUpVo)
                ?: return@composable

        SignUpFirstScreen(
            modifier = modifier,
            navController = navController,
            signUpVo = SignUpVo.fromJson(signUpVo)
        )
    }
    composable(
        route = FOneDestinations.SignUpSecond.routeWithArgs,
        arguments = FOneDestinations.SignUpSecond.arguments
    ) { navBackStackEntry ->
        val signUpVo =
            navBackStackEntry.arguments?.getString(FOneDestinations.SignUpSecond.argSignUpVo)
                ?: return@composable

        SignUpSecondScreen(
            modifier = modifier,
            navController = navController,
            signUpVo = SignUpVo.fromJson(signUpVo)
        )
    }
    composable(
        route = FOneDestinations.SignUpThird.routeWithArgs,
        arguments = FOneDestinations.SignUpThird.arguments
    ) { navBackStackEntry ->
        val signUpVo =
            navBackStackEntry.arguments?.getString(FOneDestinations.SignUpThird.argSignUpVo)
                ?: return@composable

        SignUpThirdScreen(
            modifier = modifier,
            navController = navController,
            signUpVo = SignUpVo.fromJson(signUpVo)
        )
    }
    composable(
        route = FOneDestinations.SignUpComplete.routeWithArgs,
        arguments = FOneDestinations.SignUpComplete.arguments
    ) { navBackStackEntry ->
        val signUpVo =
            navBackStackEntry.arguments?.getString(FOneDestinations.SignUpComplete.argSignUpVo)
                ?: return@composable

        SignUpCompleteScreen(
            modifier = modifier,
            signUpVo = SignUpVo.fromJson(signUpVo)
        )
    }
}