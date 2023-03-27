package com.fone.filmone.ui

sealed class FOneDestinations(val route: String) {
    object Splash : FOneDestinations("splash")
    object Login : FOneDestinations("login")
    object SignUp : FOneDestinations("sign-up")
    object Inquiry : FOneDestinations("inquiry")
    object Home : FOneDestinations("home")
}
