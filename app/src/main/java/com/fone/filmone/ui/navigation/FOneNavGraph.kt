package com.fone.filmone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.ui.email.find.FindScreen
import com.fone.filmone.ui.email.join.EmailJoinScreen
import com.fone.filmone.ui.email.login.EmailLoginScreen
import com.fone.filmone.ui.favorite.FavoriteScreen
import com.fone.filmone.ui.inquiry.InquiryScreen
import com.fone.filmone.ui.login.LoginScreen
import com.fone.filmone.ui.main.MainScreen
import com.fone.filmone.ui.main.job.filter.actor.ActorFilterScreen
import com.fone.filmone.ui.main.job.filter.staff.StaffFilterScreen
import com.fone.filmone.ui.main.model.MainBottomNavItem
import com.fone.filmone.ui.myinfo.MyInfoScreen
import com.fone.filmone.ui.myregister.MyRegisterScreen
import com.fone.filmone.ui.profile.detail.actor.ActorProfileDetailScreen
import com.fone.filmone.ui.profile.detail.staff.StaffProfileDetailScreen
import com.fone.filmone.ui.profile.edit.actor.ActorProfileEditScreen
import com.fone.filmone.ui.profile.edit.staff.StaffProfileEditScreen
import com.fone.filmone.ui.profile.list.ProfileListScreen
import com.fone.filmone.ui.profile.register.actor.ActorProfileRegisterScreen
import com.fone.filmone.ui.profile.register.staff.StaffProfileRegisterScreen
import com.fone.filmone.ui.recruiting.detail.actor.ActorRecruitingDetailScreen
import com.fone.filmone.ui.recruiting.detail.staff.StaffRecruitingDetailScreen
import com.fone.filmone.ui.recruiting.edit.actor.ActorRecruitingEditScreen
import com.fone.filmone.ui.recruiting.edit.staff.StaffRecruitingEditScreen
import com.fone.filmone.ui.recruiting.register.actor.ActorRecruitingRegisterScreen
import com.fone.filmone.ui.recruiting.register.staff.StaffRecruitingRegisterScreen
import com.fone.filmone.ui.scrap.ScrapScreen
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.signup.signUpNavGraph
import com.fone.filmone.ui.splash.SplashScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun FOneNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = FOneDestinations.Splash.route,
) {
    LaunchedEffect(key1 = "navigation") {
        FOneNavigator.routeFlow.onEach { navigationState ->
            navController.navigate(navigationState.route) {
                if (navigationState.isPopAll) {
                    popUpTo(0)
                }
            }
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(FOneDestinations.Splash.route) {
            SplashScreen()
        }
        composable(FOneDestinations.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(FOneDestinations.Inquiry.route) {
            InquiryScreen(navController = navController)
        }
        signUpNavGraph(navController = navController)
        mainNavGraph(navController)
        composable(FOneDestinations.MyInfo.route) {
            MyInfoScreen(navController = navController)
        }
        composable(FOneDestinations.Scrap.route) {
            ScrapScreen(navController = navController)
        }
        composable(FOneDestinations.Favorite.route) {
            FavoriteScreen(navController = navController)
        }
        composable(FOneDestinations.MyRegister.route) {
            MyRegisterScreen(navController = navController)
        }
        composable(FOneDestinations.ActorFilter.route) {
            ActorFilterScreen(navController = navController)
        }
        composable(FOneDestinations.StaffFilter.route) {
            StaffFilterScreen(navController = navController)
        }
        composable(FOneDestinations.ActorRecruitingRegister.route) {
            ActorRecruitingRegisterScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.ActorRecruitingEdit.routeWithContentId,
            arguments = FOneDestinations.ActorRecruitingEdit.contentIdArguments,
        ) {
            ActorRecruitingEditScreen(navController)
        }
        composable(FOneDestinations.StaffRecruitingRegister.route) {
            StaffRecruitingRegisterScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.StaffRecruitingEdit.routeWithContentId,
            arguments = FOneDestinations.StaffRecruitingEdit.contentIdArguments,
        ) {
            StaffRecruitingEditScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.ActorRecruitingDetail.routeWithArgs,
            arguments = FOneDestinations.ActorRecruitingDetail.arguments,
        ) {
            ActorRecruitingDetailScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.StaffRecruitingDetail.routeWithArgs,
            arguments = FOneDestinations.StaffRecruitingDetail.arguments,
        ) {
            StaffRecruitingDetailScreen(navController = navController)
        }
        composable(FOneDestinations.ActorProfileRegister.route) {
            ActorProfileRegisterScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.ActorProfileEdit.routeWithContentId,
            arguments = FOneDestinations.ActorProfileEdit.contentIdArguments
        ) {
            ActorProfileEditScreen(navController = navController)
        }
        composable(FOneDestinations.StaffProfileRegister.route) {
            StaffProfileRegisterScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.StaffProfileEdit.routeWithContentId,
            arguments = FOneDestinations.StaffProfileEdit.contentIdArguments,
        ) {
            StaffProfileEditScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.ActorProfileDetail.routeWithArgs,
            arguments = FOneDestinations.ActorProfileDetail.arguments,
        ) {
            ActorProfileDetailScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.StaffProfileDetail.routeWithArgs,
            arguments = FOneDestinations.StaffProfileDetail.arguments
        ) {
            StaffProfileDetailScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.ProfileList.routeWithArgs,
            arguments = FOneDestinations.ProfileList.arguments,
        ) {
            ProfileListScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.EmailLogin.route,
        ) {
            EmailLoginScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.FindIdPassword.route,
        ) {
            FindScreen(navController = navController)
        }
        composable(
            route = FOneDestinations.EmailJoin.route,
        ) {
            val savedSignupVo = it.savedStateHandle
                .get<String?>("savedSignupVo")
                ?.let(SignUpVo::fromJson)
                ?: SignUpVo()

            EmailJoinScreen(
                navController = navController,
                signUpVo = savedSignupVo
            )
        }
    }
}

private fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    composable(FOneDestinations.Main.route) {
        MainScreen(navController = navController)
    }
    composable(
        route = FOneDestinations.Main.routeWithJobInitialArgs,
        arguments = FOneDestinations.Main.jobInitialPageArguments,
    ) {
        val initialPage = it.arguments?.getString(FOneDestinations.Main.argInitialPage)
        val jobInitialPage = it.arguments?.getString(FOneDestinations.Main.argJobInitialPage)

        MainScreen(
            navController = navController,
            initialScreen = MainBottomNavItem.parsePage(initialPage ?: ""),
        )
    }
}
