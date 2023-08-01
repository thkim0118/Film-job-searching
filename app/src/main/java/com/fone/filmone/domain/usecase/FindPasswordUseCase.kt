package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.user.FindPasswordRequest
import com.fone.filmone.data.datamodel.response.user.FindPasswordResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class FindPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        code: Int,
        phoneNumber: String
    ): DataResult<FindPasswordResponse> {
        return userRepository.findPassword(
            findPasswordRequest = FindPasswordRequest(
                code = code,
                phoneNumber = phoneNumber
            )
        )
    }
}