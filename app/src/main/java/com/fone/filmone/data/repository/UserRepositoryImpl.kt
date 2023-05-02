package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.response.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import com.fone.filmone.data.datamodel.response.user.SignUpResponse
import com.fone.filmone.data.datamodel.response.user.SigninResponse
import com.fone.filmone.data.datamodel.response.user.UserResponse
import com.fone.filmone.data.datasource.local.TokenDataStore
import com.fone.filmone.data.datasource.remote.UserApi
import com.fone.filmone.di.IoDispatcher
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val tokenDataStore: TokenDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : UserRepository {
    override suspend fun checkNicknameDuplication(nickname: String): DataResult<CheckNicknameDuplicationResponse> {
        return handleNetwork { userApi.checkNicknameDuplication(nickname) }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResponse> {
        return handleNetwork { userApi.signUp(signUpRequest) }
    }

    override suspend fun signIn(signinRequest: SigninRequest): DataResult<SigninResponse> {
        return handleNetwork { userApi.signIn(signinRequest) }
            .onSuccess(dispatcher) { signInResponse ->
                if (signInResponse != null) {
                    tokenDataStore.saveAccessToken(signInResponse.token.accessToken)
                    tokenDataStore.saveRefreshToken(signInResponse.token.refreshToken)
                }
            }
    }

    override suspend fun signOut(): DataResult<Unit> {
        return handleNetwork { userApi.signOut() }
            .onSuccess(dispatcher) {
                tokenDataStore.clearToken()
            }
    }

    override suspend fun getUserInfo(): DataResult<UserResponse> {
        return handleNetwork { userApi.getUserInfo() }
    }

    override suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): DataResult<UserResponse> {
        return handleNetwork { userApi.updateUserInfo(userUpdateRequest) }
    }

    override suspend fun logout(): DataResult<Unit> {
        return handleNetwork { userApi.logout() }
            .onSuccess(dispatcher) {
                tokenDataStore.clearToken()
            }
    }
}