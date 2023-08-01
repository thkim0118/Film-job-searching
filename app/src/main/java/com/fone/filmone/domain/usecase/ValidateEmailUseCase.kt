package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.user.EmailValidationRequest
import com.fone.filmone.data.datamodel.response.user.EmailValidationResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(code: String, email: String): DataResult<EmailValidationResponse> {
        return userRepository.validateEmail(
            emailValidationRequest = EmailValidationRequest(code = code, email = email)
        )
    }
}