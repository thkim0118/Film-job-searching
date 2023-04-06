package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.common.NetworkResponse
import com.fone.filmone.data.datamodel.response.sms.SmsVerificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface SmsApi {
    @POST
    suspend fun requestSmsVerify(): Response<NetworkResponse<Unit>>
    @GET
    suspend fun verifySmsCode(): Response<NetworkResponse<SmsVerificationResponse>>
}