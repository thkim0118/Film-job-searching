package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.response.common.handleNetwork
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import com.fone.filmone.data.datamodel.response.user.SignUpResponse
import com.fone.filmone.data.datasource.remote.UserApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun checkNicknameDuplication(nickname: String): DataResult<CheckNicknameDuplicationResponse> {
        return handleNetwork { userApi.checkNicknameDuplication(nickname) }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResponse> {
        return handleNetwork { userApi.signUp(signUpRequest) }
    }
}