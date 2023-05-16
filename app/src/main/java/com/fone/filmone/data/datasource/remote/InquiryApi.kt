package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.request.inquiry.InquiryRequest
import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.response.inquiry.InquiryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InquiryApi {
    @POST("${Server.ApiVersion}/questions")
    suspend fun submitInquiry(
        @Body inquiryRequest: InquiryRequest
    ) : Response<NetworkResponse<InquiryResponse>>
}
