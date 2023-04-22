package com.fone.filmone.framework.drivers

import com.fone.filmone.domain.repository.auth.AuthRepository
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authRepository: AuthRepository
) : Interceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = runBlocking { authRepository.getAccessToken() }

        val authenticatedRequest = if (accessToken.isNotEmpty()) {
            request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        } else {
            request
        }

        val response = chain.proceed(authenticatedRequest)

        if (response.code == 401 || response.code == 403) {
            synchronized(this) {
                val newAccessToken = runBlocking { authRepository.refreshToken() }

                if (newAccessToken.isNotEmpty()) {
                    val newRequest = request.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()

                    return chain.proceed(newRequest)
                } else {
                    FOneNavigator.navigateTo(
                        navDestinationState = NavDestinationState(
                            route = FOneDestinations.Login.route,
                            isPopAll = true
                        )
                    )
                }
            }
        }

        return response
    }
}