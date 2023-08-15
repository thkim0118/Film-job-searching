package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.user.ChangePasswordRequest
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        password: String,
        phoneNumber: String,
        token: String
    ): DataResult<Unit> {
        return userRepository.changePassword(
            changePasswordRequest = ChangePasswordRequest(
                password = password,
                phoneNumber = phoneNumber,
                token = token
            )
        )
    }
}
