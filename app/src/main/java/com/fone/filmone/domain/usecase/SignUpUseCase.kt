package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.user.SignUpResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.signup.model.SignUpVo.Companion.mapToSignUpRequest
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(signUpVo: SignUpVo): DataResult<SignUpResponse> {
        return userRepository.signUp(signUpVo.mapToSignUpRequest())
    }
}
