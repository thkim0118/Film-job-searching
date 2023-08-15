package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.request.sms.SmsRequest
import com.fone.filmone.data.datamodel.response.sms.SmsTransmitResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsApi {
    @POST("prod/send-sms")
    suspend fun requestSmsVerifyCode(
        @Body smsRequest: SmsRequest
    ): Response<NetworkResponse<SmsTransmitResponse>>
}
