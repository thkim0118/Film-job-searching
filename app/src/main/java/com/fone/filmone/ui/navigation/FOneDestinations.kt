package com.fone.filmone.ui.navigation

sealed class FOneDestinations(val route: String) {
    object Splash : FOneDestinations("splash")
    object Login : FOneDestinations("login")
    object SignUp : FOneDestinations("sign-up") {
        object SignUpFirst : FOneDestinations("sign-up/first")
        object SignUpSecond : FOneDestinations("sign-up/second")
        object SignUpThird : FOneDestinations("sign-up/third")
        object SignUpComplete : FOneDestinations("sign-up/complete")
    }
    object Inquiry : FOneDestinations("inquiry")
    object Home : FOneDestinations("home")
}
