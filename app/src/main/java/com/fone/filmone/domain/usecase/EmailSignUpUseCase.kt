package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.user.EmailSignUpResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.signup.model.SignUpVo.Companion.mapToEmailSignUpRequest
import javax.inject.Inject

class EmailSignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(signUpVo: SignUpVo): DataResult<EmailSignUpResponse> {
        return userRepository.emailSignUp(signUpVo.mapToEmailSignUpRequest())
    }
}