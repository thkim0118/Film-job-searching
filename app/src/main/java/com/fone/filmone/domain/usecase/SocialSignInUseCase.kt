package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.fone.filmone.data.datamodel.response.user.SigninResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class SocialSignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        email: String,
        socialLoginType: LoginType
    ): DataResult<SigninResponse> {
        return userRepository.signIn(
            SigninRequest(
                accessToken = accessToken,
                email = email,
                loginType = socialLoginType
            )
        )
    }
}
