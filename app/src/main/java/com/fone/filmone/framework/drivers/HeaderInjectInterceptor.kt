package com.fone.filmone.framework.drivers

import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInjectInterceptor @Inject constructor(
    private val authRepository: AuthRepository,
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

        LogUtil.i("Headers :: ${authenticatedRequest.headers.toMultimap()}")

        return chain.proceed(authenticatedRequest)
    }
}
