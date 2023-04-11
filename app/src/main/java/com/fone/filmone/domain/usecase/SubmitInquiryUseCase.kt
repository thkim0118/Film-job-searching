package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.inquiry.InquiryRequest
import com.fone.filmone.data.datamodel.response.inquiry.InquiryResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.inquiry.InquiryVo
import com.fone.filmone.domain.repository.inquiry.InquiryRepository
import javax.inject.Inject

class SubmitInquiryUseCase @Inject constructor(
    private val inquiryRepository: InquiryRepository
) {
    suspend operator fun invoke(inquiryVo: InquiryVo): DataResult<InquiryResponse> {
        return inquiryRepository.submitInquiry(
            InquiryRequest(
                email = inquiryVo.email,
                type = inquiryVo.type.name,
                title = inquiryVo.title,
                description = inquiryVo.description,
                agreeToPersonalInformation = inquiryVo.agreeToPersonalInformation,
            )
        )
    }
}