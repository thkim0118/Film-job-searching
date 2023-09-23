package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.user.CheckEmailDuplicationRequest
import com.fone.filmone.data.datamodel.response.user.CheckEmailDuplicationResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class CheckEmailDuplicationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String): DataResult<CheckEmailDuplicationResponse> {
        return userRepository.checkEmailDuplication(
            checkEmailDuplicationRequest = CheckEmailDuplicationRequest(email)
        )
    }
}
