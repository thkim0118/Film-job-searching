package com.fone.filmone.data.datamodel.response.common.user

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.fone.filmone.R

@Keep
enum class Gender(@StringRes val stringRes: Int) {
    MAN(R.string.gender_man),
    WOMAN(R.string.gender_woman),
    IRRELEVANT(R.string.gender_irrelevant)
}