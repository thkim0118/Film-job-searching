package com.fone.filmone.domain.usecase

import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.toMappedDataResult
import com.fone.filmone.domain.repository.user.UserRepository
import javax.inject.Inject

class CheckNicknameDuplicationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String): DataResult<Boolean> {
        return userRepository.checkNicknameDuplication(nickname = nickname)
            .toMappedDataResult { it.isDuplicate }
    }
}
