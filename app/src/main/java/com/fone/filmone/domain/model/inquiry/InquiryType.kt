package com.fone.filmone.domain.model.inquiry

import androidx.annotation.StringRes
import com.fone.filmone.R

enum class InquiryType(@StringRes val titleRes: Int) {
    USE_QUESTION(R.string.inquiry_type_using_service),
    ALLIANCE(R.string.inquiry_type_partnership),
    VOICE_OF_THE_CUSTOMER(R.string.inquiry_type_user_report)
}
