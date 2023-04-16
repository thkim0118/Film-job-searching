package com.fone.filmone.ui.main

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.fone.filmone.ui.main.chat.ChatScreen
import com.fone.filmone.ui.main.home.HomeScreen
import com.fone.filmone.ui.main.job.JobScreen
import com.fone.filmone.ui.main.my.MyScreen
import com.fone.filmone.ui.navigation.FOneDestinations

fun NavGraphBuilder.mainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    composable(
        route = FOneDestinations.Home.route,
    ) {
        HomeScreen(
            modifier = modifier,
        )
    }
    composable(
        route = FOneDestinations.Job.route,
    ) {
        JobScreen(
            modifier = modifier,
        )
    }
    composable(
        route = FOneDestinations.Chat.route,
    ) {
        ChatScreen(
            modifier = modifier,
        )
    }
    composable(
        route = FOneDestinations.My.route,
    ) {
        MyScreen(
            modifier = modifier,
        )
    }
}