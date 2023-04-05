package com.fone.filmone.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fone.filmone.ui.signup.model.SignUpVo

sealed class FOneDestinations(val route: String) {
    object Splash : FOneDestinations("splash")
    object Login : FOneDestinations("login")
    object SignUp : FOneDestinations("sign-up") {
        const val argSignUpVo = "arg_sign_up_vo"
        object SignUpFirst : FOneDestinations("sign-up/first")
        object SignUpSecond : FOneDestinations("sign-up/second") {
            val routeWithArgs = "$route/{$argSignUpVo}"
            val arguments = listOf(
                navArgument(argSignUpVo) { type = NavType.StringType }
            )

            fun getRouteWithArg(signUpFirstVo: SignUpVo): String {
                return "$route/${SignUpVo.toJson(signUpFirstVo)}"
            }
        }

        object SignUpThird : FOneDestinations("sign-up/third") {
            val routeWithArgs = "$route/{$argSignUpVo}"
            val arguments = listOf(
                navArgument(argSignUpVo) { type = NavType.StringType }
            )

            fun getRouteWithArg(signUpVo: SignUpVo): String {
                return "$route/${SignUpVo.toJson(signUpVo)}"
            }
        }
        object SignUpComplete : FOneDestinations("sign-up/complete")
    }

    object Inquiry : FOneDestinations("inquiry")
    object Home : FOneDestinations("home")
}
