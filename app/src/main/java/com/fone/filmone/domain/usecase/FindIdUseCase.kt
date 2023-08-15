package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.user.FindIdRequest
import com.fone.filmone.data.datamodel.response.user.FindIdResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class FindIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        code: Int,
        phoneNumber: String
    ): DataResult<FindIdResponse> {
        return userRepository.findId(
            FindIdRequest(code = code, phoneNumber = phoneNumber)
        )
    }
}
