package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.user.EmailSignInRequest
import com.fone.filmone.data.datamodel.response.user.EmailSignInResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class EmailSignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): DataResult<EmailSignInResponse> {
        return userRepository.emailSignIn(
            emailSignInRequest = EmailSignInRequest(
                email = email,
                password = password,
            )
        )
    }
}