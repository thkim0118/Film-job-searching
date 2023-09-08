package com.fone.filmone.domain.repository

interface AuthRepository {
    suspend fun refreshToken(): String
    suspend fun getAccessToken(): String
    suspend fun clearToken()
}
