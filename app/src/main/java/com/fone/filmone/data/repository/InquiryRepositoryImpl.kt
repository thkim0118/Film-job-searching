package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.request.inquiry.InquiryRequest
import com.fone.filmone.data.datamodel.response.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.inquiry.InquiryResponse
import com.fone.filmone.data.datasource.remote.InquiryApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.InquiryRepository
import javax.inject.Inject

class InquiryRepositoryImpl @Inject constructor(
    private val inquiryApi: InquiryApi
) : InquiryRepository {
    override suspend fun submitInquiry(inquiryRequest: InquiryRequest): DataResult<InquiryResponse> {
        return handleNetwork { inquiryApi.submitInquiry(inquiryRequest) }
    }
}