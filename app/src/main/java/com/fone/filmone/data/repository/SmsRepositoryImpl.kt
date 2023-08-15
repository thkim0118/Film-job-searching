package com.fone.filmone.data.repository

import com.fone.filmone.BuildConfig
import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.request.sms.SmsRequest
import com.fone.filmone.data.datamodel.response.sms.SmsTransmitResponse
import com.fone.filmone.data.datasource.remote.SmsApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.SmsRepository
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(
    private val smsApi: SmsApi
) : SmsRepository {
    private var verificationCode: String = ""

    override suspend fun requestSmsCode(smsRequest: SmsRequest): DataResult<SmsTransmitResponse> {
        verificationCode = smsRequest.code

        return if (BuildConfig.DEBUG) {
            DataResult.Success(SmsTransmitResponse("TEST", verificationCode))
        } else {
            handleNetwork { smsApi.requestSmsVerifyCode(smsRequest) }
        }
    }

    override fun verifySmsVerificationCode(code: String): DataResult<Boolean> {
        if (verificationCode.isEmpty()) {
            return DataResult.EmptyData
        }

        return DataResult.Success(verificationCode == code)
            .onSuccess { isVerify ->
                if (isVerify == null) {
                    return@onSuccess
                }

                if (isVerify) {
                    verificationCode = ""
                }
            }
    }
}
