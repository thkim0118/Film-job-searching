package com.fone.filmone.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object {
        private const val TOKEN_PREFERENCES_NAME = "token_preferences"
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    private val Context.createTokenDataStore by preferencesDataStore(
        name = TOKEN_PREFERENCES_NAME
    )

    private val tokenDataStore: DataStore<Preferences> = context.createTokenDataStore

    suspend fun saveAccessToken(accessToken: String) {
        tokenDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        tokenDataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun clearToken() {
        tokenDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = ""
            preferences[REFRESH_TOKEN_KEY] = ""
        }
    }

    suspend fun getAccessToken(): String? {
        val preferences = tokenDataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
        return preferences.firstOrNull()
    }

    suspend fun getRefreshToken(): String? {
        val preferences = tokenDataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
        return preferences.firstOrNull()
    }
}
