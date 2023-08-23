package com.fone.filmone.framework.drivers

import com.fone.filmone.domain.repository.AuthRepository
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection
import javax.inject.Inject

class RefreshAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            synchronized(this) {
                val newAccessToken = runBlocking { authRepository.refreshToken() }

                if (newAccessToken.isNotEmpty()) {
                    return response
                        .request
                        .newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newAccessToken")
                        .build()
                } else {
                    FOneNavigator.navigateTo(
                        navDestinationState = NavDestinationState(
                            route = FOneDestinations.Login.route,
                            isPopAll = true,
                        ),
                    )
                }
            }
        }

        return response.request
    }
}
