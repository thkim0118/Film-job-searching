package com.fone.filmone.domain.repository.auth

interface AuthRepository {
    suspend fun refreshToken(): String
    suspend fun getAccessToken(): String
}