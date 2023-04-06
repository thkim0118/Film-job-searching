package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.request.sms.SmsRequest
import com.fone.filmone.data.datamodel.response.common.handleNetwork
import com.fone.filmone.data.datamodel.response.sms.SmsTransmitResponse
import com.fone.filmone.data.datasource.remote.SmsApi
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.sms.SmsRepository
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(
    private val smsApi: SmsApi
) : SmsRepository {
    private var verificationCode: String = ""

    override suspend fun requestSmsCode(smsRequest: SmsRequest): DataResult<SmsTransmitResponse> {
        verificationCode = smsRequest.code
        return handleNetwork { smsApi.requestSmsVerifyCode(smsRequest) }
    }

    override fun verifySmsVerificationCode(code: String): DataResult<Boolean> {
        if (verificationCode.isEmpty()) {
            return DataResult.EmptyData(null)
        }

        return DataResult.Success(verificationCode == code).onSuccess {
            verificationCode = ""
        }
    }
}