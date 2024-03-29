package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.data.datamodel.response.user.UserResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        interests: List<Category>,
        job: Job,
        nickname: String,
        profileUrl: String
    ): DataResult<UserResponse> {
        return userRepository.updateUserInfo(
            UserUpdateRequest(
                interests = interests,
                job = job,
                nickname = nickname,
                profileUrl = profileUrl
            )
        )
    }
}
