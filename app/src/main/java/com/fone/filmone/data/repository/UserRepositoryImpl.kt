package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.response.common.NetworkResult
import com.fone.filmone.data.datamodel.response.common.handleNetwork
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import com.fone.filmone.data.datasource.remote.UserApi
import com.fone.filmone.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun checkNicknameDuplication(nickname: String): NetworkResult<CheckNicknameDuplicationResponse> {
        return handleNetwork { userApi.checkNicknameDuplication(nickname) }
    }
}