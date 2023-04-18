package com.fone.filmone.domain.repository.user

import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.response.user.*
import com.fone.filmone.domain.model.common.DataResult

interface UserRepository {
    suspend fun checkNicknameDuplication(nickname: String): DataResult<CheckNicknameDuplicationResponse>
    suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResponse>
    suspend fun signIn(signinRequest: SigninRequest): DataResult<SigninResponse>
    suspend fun getUserInfo(): DataResult<UserResponse>
}