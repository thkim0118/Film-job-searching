package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.response.common.handleNetwork
import com.fone.filmone.data.datamodel.response.sms.SmsVerificationResponse
import com.fone.filmone.data.datasource.remote.SmsApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.sms.SmsRepository
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(
    private val smsApi: SmsApi
) : SmsRepository {
    override suspend fun requestSmsCode(): DataResult<Unit> {
        return handleNetwork { smsApi.requestSmsVerify() }
    }

    override suspend fun verifySmsCode(
        phoneNumber: String,
        code: String
    ): DataResult<SmsVerificationResponse> {
        return handleNetwork { smsApi.verifySmsCode() }
    }
}