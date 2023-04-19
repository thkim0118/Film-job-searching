package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.request.user.RefreshTokenRequest
import com.fone.filmone.data.datamodel.response.common.handleNetwork
import com.fone.filmone.data.datamodel.response.user.Token
import com.fone.filmone.data.datasource.local.TokenDataStore
import com.fone.filmone.data.datasource.remote.TokenApi
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.repository.auth.AuthRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val tokenApi: TokenApi
) : AuthRepository {

    private val refreshTokenMutex = Mutex()

    override suspend fun getAccessToken(): String {
        val accessToken = tokenDataStore.getAccessToken() ?: ""

        if (accessToken.isNotEmpty()) {
            return accessToken
        }

        return refreshToken()
    }

    override suspend fun refreshToken(): String {
        refreshTokenMutex.withLock {
            if (tokenDataStore.getAccessToken().isNullOrEmpty()) {
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
}