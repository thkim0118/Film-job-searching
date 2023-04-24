package com.fone.filmone.data.repository

import com.fone.filmone.data.datasource.local.TokenDataStore
import com.fone.filmone.domain.repository.token.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : TokenRepository {
    override suspend fun clearToken() {
        tokenDataStore.clearToken()
    }
}