package com.fone.filmone.domain.usecase

import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): DataResult<Unit> {
        return userRepository.logout()
    }
}
