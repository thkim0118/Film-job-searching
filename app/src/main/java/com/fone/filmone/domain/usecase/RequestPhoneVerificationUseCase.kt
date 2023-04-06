package com.fone.filmone.domain.usecase

import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.sms.SmsRepository
import javax.inject.Inject

class RequestPhoneVerificationUseCase @Inject constructor(
    private val smsRepository: SmsRepository
) {
    suspend operator fun invoke(): DataResult<Unit> {
        return smsRepository.requestSmsCode()
    }
}