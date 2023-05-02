package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.request.inquiry.InquiryRequest
import com.fone.filmone.data.datamodel.response.inquiry.InquiryResponse
import com.fone.filmone.domain.model.common.DataResult

interface InquiryRepository {
    suspend fun submitInquiry(inquiryRequest: InquiryRequest): DataResult<InquiryResponse>
}