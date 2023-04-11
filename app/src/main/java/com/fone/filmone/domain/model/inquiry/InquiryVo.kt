package com.fone.filmone.domain.model.inquiry

data class InquiryVo(
    val agreeToPersonalInformation: Boolean,
    val description: String,
    val email: String,
    val id: Int,
    val title: String,
    val type: InquiryType
)