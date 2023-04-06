package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.sms.SmsVerificationResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.sms.SmsRepository
import javax.inject.Inject

class VerifySmsCodeUseCase @Inject constructor(
    private val smsRepository: SmsRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        code: String
    ): DataResult<SmsVerificationResponse> {
        return smsRepository.verifySmsCode(phoneNumber, code)
    }
}
