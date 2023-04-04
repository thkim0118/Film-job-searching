package com.fone.filmone.domain.repository.user

import com.fone.filmone.data.datamodel.response.common.NetworkResult
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse

interface UserRepository {

    suspend fun checkNicknameDuplication(nickname: String): NetworkResult<CheckNicknameDuplicationResponse>
}