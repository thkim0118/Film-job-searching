package com.fone.filmone.domain.repository.sms

import com.fone.filmone.data.datamodel.request.sms.SmsRequest
import com.fone.filmone.data.datamodel.response.sms.SmsTransmitResponse
import com.fone.filmone.domain.model.common.DataResult

interface SmsRepository {
    suspend fun requestSmsCode(smsRequest: SmsRequest): DataResult<SmsTransmitResponse>
    fun verifySmsVerificationCode(code: String): DataResult<Boolean>
}