package com.fone.filmone.domain.repository.sms

import com.fone.filmone.data.datamodel.response.sms.SmsVerificationResponse
import com.fone.filmone.domain.model.common.DataResult

interface SmsRepository {
    suspend fun requestSmsCode(): DataResult<Unit>
    suspend fun verifySmsCode(
        phoneNumber: String,
        code: String
    ): DataResult<SmsVerificationResponse>
}