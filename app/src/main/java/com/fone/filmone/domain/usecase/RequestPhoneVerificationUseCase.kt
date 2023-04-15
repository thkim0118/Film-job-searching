package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.sms.SmsRequest
import com.fone.filmone.data.datamodel.response.sms.SmsTransmitResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.sms.SmsRepository
import javax.inject.Inject
import kotlin.random.Random

class RequestPhoneVerificationUseCase @Inject constructor(
    private val smsRepository: SmsRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        verificationCode: String = Random.nextInt(100_000, 999_999).toString()
    ): DataResult<SmsTransmitResponse> {
        return smsRepository.requestSmsCode(
            SmsRequest(
                phone = phoneNumber,
                code = verificationCode
            )
        )
    }
}