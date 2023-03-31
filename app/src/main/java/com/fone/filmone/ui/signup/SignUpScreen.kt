package com.fone.filmone.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.ui.FOneDestinations
import com.fone.filmone.ui.signup.navigation.SignUpNavGraph

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = FOneDestinations.SignUp.SignUpFirst.route
) {
    SignUpNavGraph(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    )
}