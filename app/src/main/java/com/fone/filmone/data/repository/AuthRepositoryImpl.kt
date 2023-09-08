package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.request.user.RefreshTokenRequest
import com.fone.filmone.data.datamodel.response.user.Token
import com.fone.filmone.data.datasource.local.TokenDataStore
import com.fone.filmone.data.datasource.remote.TokenApi
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.repository.AuthRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val tokenApi: TokenApi,
) : AuthRepository {

    private val refreshTokenMutex = Mutex()

    override suspend fun getAccessToken(): String {
        return tokenDataStore.getAccessToken() ?: ""
    }

    override suspend fun refreshToken(): String {
        val accessToken = tokenDataStore.getAccessToken()
        refreshTokenMutex.withLock {
            val currentAccessToken = tokenDataStore.getAccessToken()

            if (accessToken.isNullOrEmpty() && currentAccessToken.isNullOrEmpty()) {
                return ""
            }

            if (accessToken == currentAccessToken) {
                val result = handleNetwork {
                    tokenApi.refreshToken(
                        RefreshTokenRequest(
                            accessToken = tokenDataStore.getAccessToken() ?: "",
                            refreshToken = tokenDataStore.getRefreshToken() ?: ""
                        )
                    )
                }

                val token: Token? = result.getOrNull()

                if (token != null) {
                    with(tokenDataStore) {
                        saveAccessToken(token.accessToken)
                        saveRefreshToken(token.refreshToken)
                    }
                }

                return token?.accessToken ?: ""
            } else {
                return tokenDataStore.getAccessToken() ?: ""
            }
        }
    }

    override suspend fun clearToken() {
        tokenDataStore.clearToken()
    }
}
