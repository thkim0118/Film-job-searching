package com.fone.filmone.data.repository

import com.fone.filmone.data.datasource.local.TokenDataStore
import com.fone.filmone.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun refreshToken(): String {
        return ""
    }

    override suspend fun getAccessToken(): String {
        return tokenDataStore.getAccessToken() ?: ""
    }

    override suspend fun getRefreshToken(): String {
        return tokenDataStore.getRefreshToken() ?: ""
    }
}