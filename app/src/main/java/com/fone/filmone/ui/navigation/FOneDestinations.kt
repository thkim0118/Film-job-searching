package com.fone.filmone.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fone.filmone.ui.signup.model.SignUpFirstVo

sealed class FOneDestinations(val route: String) {
    object Splash : FOneDestinations("splash")
    object Login : FOneDestinations("login")
    object SignUp : FOneDestinations("sign-up") {
        object SignUpFirst : FOneDestinations("sign-up/first")
        object SignUpSecond : FOneDestinations("sign-up/second") {
            const val argSignUpFirstModel = "arg_first_model"

            val routeWithArgs = "$route/{$argSignUpFirstModel}"
            val arguments = listOf(
                navArgument(argSignUpFirstModel) { type = NavType.StringType }
            )

            fun getRouteWithArg(signUpFirstVo: SignUpFirstVo): String {
                return "$route/${SignUpFirstVo.toJson(signUpFirstVo)}"
            }
        }

        object SignUpThird : FOneDestinations("sign-up/third")
        object SignUpComplete : FOneDestinations("sign-up/complete")
    }

    object Inquiry : FOneDestinations("inquiry")
    object Home : FOneDestinations("home")
}
