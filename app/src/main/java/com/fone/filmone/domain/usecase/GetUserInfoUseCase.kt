package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.user.UserResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): DataResult<UserResponse> {
        return userRepository.getUserInfo()
    }
}
