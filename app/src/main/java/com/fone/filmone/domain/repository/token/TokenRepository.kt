package com.fone.filmone.domain.repository.token

interface TokenRepository {
    suspend fun clearToken()
}