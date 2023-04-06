package com.fone.filmone.domain.repository.user

import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import com.fone.filmone.data.datamodel.response.user.SignUpResponse
import com.fone.filmone.domain.model.common.DataResult

interface UserRepository {
    suspend fun checkNicknameDuplication(nickname: String): DataResult<CheckNicknameDuplicationResponse>
    suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResponse>
}