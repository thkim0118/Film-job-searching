package com.fone.filmone.domain.usecase

import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.sms.SmsRepository
import javax.inject.Inject

class VerifySmsCodeUseCase @Inject constructor(
    private val smsRepository: SmsRepository
) {
    operator fun invoke(code: String): DataResult<Boolean> {
        return smsRepository.verifySmsVerificationCode(code)
    }
}
