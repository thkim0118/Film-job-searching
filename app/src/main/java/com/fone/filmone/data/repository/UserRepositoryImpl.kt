package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.request.user.ChangePasswordRequest
import com.fone.filmone.data.datamodel.request.user.CheckEmailDuplicationRequest
import com.fone.filmone.data.datamodel.request.user.EmailSignInRequest
import com.fone.filmone.data.datamodel.request.user.EmailSignUpRequest
import com.fone.filmone.data.datamodel.request.user.EmailValidationRequest
import com.fone.filmone.data.datamodel.request.user.FindIdRequest
import com.fone.filmone.data.datamodel.request.user.FindPasswordRequest
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.request.user.ValidatePasswordRequest
import com.fone.filmone.data.datamodel.response.user.CheckEmailDuplicationResponse
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import com.fone.filmone.data.datamodel.response.user.EmailSignInResponse
import com.fone.filmone.data.datamodel.response.user.EmailSignUpResponse
import com.fone.filmone.data.datamodel.response.user.EmailValidationResponse
import com.fone.filmone.data.datamodel.response.user.FindIdResponse
import com.fone.filmone.data.datamodel.response.user.FindPasswordResponse
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
                    saveUserToken(
                        accessToken = signInResponse.token.accessToken,
                        refreshToken = signInResponse.token.refreshToken
                    )
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

    override suspend fun emailSignIn(emailSignInRequest: EmailSignInRequest): DataResult<EmailSignInResponse> {
        return handleNetwork { userApi.emailSignIn(emailSignInRequest) }
            .onSuccess(dispatcher) { response ->
                if (response != null) {
                    saveUserToken(
                        accessToken = response.token.accessToken,
                        refreshToken = response.token.refreshToken
                    )
                }
            }
    }

    override suspend fun emailSignUp(emailSignUpRequest: EmailSignUpRequest): DataResult<EmailSignUpResponse> {
        return handleNetwork { userApi.emailSignUp(emailSignUpRequest) }
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): DataResult<Unit> {
        return handleNetwork { userApi.changePassword(changePasswordRequest) }
    }

    override suspend fun validatePassword(validatePasswordRequest: ValidatePasswordRequest): DataResult<Unit> {
        return handleNetwork { userApi.validatePassword(validatePasswordRequest = validatePasswordRequest) }
    }

    override suspend fun findId(findIdRequest: FindIdRequest): DataResult<FindIdResponse> {
        return handleNetwork { userApi.findId(findIdRequest) }
    }

    override suspend fun findPassword(findPasswordRequest: FindPasswordRequest): DataResult<FindPasswordResponse> {
        return handleNetwork { userApi.findPassword(findPasswordRequest) }
    }

    override suspend fun validateEmail(emailValidationRequest: EmailValidationRequest): DataResult<EmailValidationResponse> {
        return handleNetwork { userApi.validateEmail(emailValidationRequest) }
    }

    override suspend fun checkEmailDuplication(checkEmailDuplicationRequest: CheckEmailDuplicationRequest): DataResult<CheckEmailDuplicationResponse> {
        return handleNetwork { userApi.checkEmailDuplication(checkEmailDuplicationRequest) }
    }

    private suspend fun saveUserToken(
        accessToken: String,
        refreshToken: String
    ) {
        tokenDataStore.saveAccessToken(accessToken)
        tokenDataStore.saveRefreshToken(refreshToken)
    }
}
