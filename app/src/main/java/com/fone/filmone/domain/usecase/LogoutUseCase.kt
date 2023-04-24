package com.fone.filmone.domain.usecase

import com.fone.filmone.di.IoDispatcher
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.token.TokenRepository
import com.fone.filmone.domain.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): DataResult<Unit> {
        return userRepository.logout()
            .onSuccess(dispatcher) {
                tokenRepository.clearToken()
            }
    }
}