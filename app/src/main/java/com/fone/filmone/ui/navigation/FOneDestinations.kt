package com.fone.filmone.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fone.filmone.ui.main.model.MainBottomNavItem
import com.fone.filmone.ui.profile.list.model.ProfileListArguments
import com.fone.filmone.ui.signup.model.SignUpVo

sealed class FOneDestinations(val route: String) {
    object Splash : FOneDestinations("splash")
    object Login : FOneDestinations("login")
    object SignUpFirst : FOneDestinations("sign-up/first") {
        const val argSignUpVo = "arg_sign_up_vo"
        val routeWithArgs = "$route/{$argSignUpVo}"
        val arguments = listOf(
            navArgument(argSignUpVo) { type = NavType.StringType }
        )

        fun getRouteWithArg(signUpFirstVo: SignUpVo): String {
            return "$route/${SignUpVo.toJson(signUpFirstVo)}"
        }
    }

    object SignUpSecond : FOneDestinations("sign-up/second") {
        const val argSignUpVo = "arg_sign_up_vo"
        val routeWithArgs = "$route/{$argSignUpVo}"
        val arguments = listOf(
            navArgument(argSignUpVo) { type = NavType.StringType }
        )

        fun getRouteWithArg(signUpFirstVo: SignUpVo): String {
            return "$route/${SignUpVo.toJson(signUpFirstVo)}"
        }
    }

    object SignUpThird : FOneDestinations("sign-up/third") {
        const val argSignUpVo = "arg_sign_up_vo"
        val routeWithArgs = "$route/{$argSignUpVo}"
        val arguments = listOf(
            navArgument(argSignUpVo) { type = NavType.StringType }
        )

        fun getRouteWithArg(signUpVo: SignUpVo): String {
            return "$route/${SignUpVo.toJson(signUpVo)}"
        }
    }

    object SignUpComplete : FOneDestinations("sign-up/complete") {
        const val argAccessToken = "arg_access_token"
        const val argEmail = "arg_email"
        const val argSocialLoginType = "arg_social_login_type"
        const val argNickname = "arg_nickname"
        const val argPassword = "arg_password"
        val routeWithArgs =
            "$route/{$argAccessToken}/{$argEmail}/{$argSocialLoginType}/{$argNickname}/{$argPassword}"
        val arguments = listOf(
            navArgument(argAccessToken) { type = NavType.StringType },
            navArgument(argEmail) { type = NavType.StringType },
            navArgument(argSocialLoginType) { type = NavType.StringType },
            navArgument(argNickname) { type = NavType.StringType },
            navArgument(argPassword) { type = NavType.StringType }
        )

        fun getRouteWithArg(
            accessToken: String?,
            email: String,
            socialLoginType: String,
            nickname: String,
            password: String?
        ): String {
            return "$route/$accessToken/$email/$socialLoginType/$nickname/$password"
        }
    }

    object Inquiry : FOneDestinations("inquiry")
    object Main : FOneDestinations("main") {
        const val argInitialPage = "arg_initial_page"
        const val argJobInitialPage = "arg_job_initial_page"
        val routeWithJobInitialArgs = "$route/{$argInitialPage}/{$argJobInitialPage}"
        val jobInitialPageArguments = listOf(
            navArgument(argInitialPage) { type = NavType.StringType },
            navArgument(argJobInitialPage) { type = NavType.StringType },
        )
        fun getRouteWithJobInitialPageArg(jobInitialPage: String = MainBottomNavItem.Home.name): String {
            return "$route/${MainBottomNavItem.Job.name}/$jobInitialPage"
        }
    }
    object MyInfo : FOneDestinations("my-info")
    object Scrap : FOneDestinations("scrap")
    object Favorite : FOneDestinations("favorite")
    object MyRegister : FOneDestinations("my-register")
    object ActorFilter : FOneDestinations("actor-filter")
    object StaffFilter : FOneDestinations("staff_filter")
    object ActorRecruitingRegister : FOneDestinations("actor_recruiting_register")
    object StaffRecruitingRegister : FOneDestinations("staff_recruiting_register")
    object ActorRecruitingDetail : FOneDestinations("actor_recruiting_detail") {
        const val argActorRecruitingDetail = "arg_actor_recruiting_detail"
        val routeWithArgs = "$route/{$argActorRecruitingDetail}"
        val arguments = listOf(
            navArgument(argActorRecruitingDetail) { type = NavType.IntType }
        )

        fun getRouteWithArg(competitionId: Int): String {
            return "$route/$competitionId"
        }
    }

    object StaffRecruitingDetail : FOneDestinations("staff_recruiting_detail") {
        const val argStaffRecruitingDetail = "arg_actor_recruiting_detail"
        val routeWithArgs = "$route/{$argStaffRecruitingDetail}"
        val arguments = listOf(
            navArgument(argStaffRecruitingDetail) { type = NavType.IntType }
        )

        fun getRouteWithArg(competitionId: Int): String {
            return "$route/$competitionId"
        }
    }

    object ActorProfileRegister : FOneDestinations("actor_profile_register")
    object StaffProfileRegister : FOneDestinations("staff_profile_register")
    object ActorProfileDetail : FOneDestinations("actor_profile_detail") {
        const val argActorProfileDetail = "arg_actor_profile_detail"
        val routeWithArgs = "$route/{$argActorProfileDetail}"
        val arguments = listOf(
            navArgument(argActorProfileDetail) { type = NavType.IntType }
        )

        fun getRouteWithArg(profileId: Int): String {
            return "$route/$profileId"
        }
    }

    object StaffProfileDetail : FOneDestinations("staff_profile_detail")
    object ProfileList : FOneDestinations("profile_list") {
        const val argProfileList = "arg_profile_list"
        val routeWithArgs = "$route/{$argProfileList}"
        val arguments = listOf(
            navArgument(argProfileList) { type = NavType.StringType },
        )

        fun getRouteWithArg(profileListArgument: ProfileListArguments): String {
            return "$route/${ProfileListArguments.toJson(profileListArgument)}"
        }
    }

    object EmailLogin : FOneDestinations("email_login")
    object FindIdPassword : FOneDestinations("find_id_password")
    object EmailJoin : FOneDestinations("email_join")
}
