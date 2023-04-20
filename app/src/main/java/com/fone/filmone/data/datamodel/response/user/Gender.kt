package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.fone.filmone.R

@Keep
enum class Gender(@StringRes val stringRes: Int) {
    IRRELEVANT(R.string.gender_irrelevant),
    MAN(R.string.gender_man),
    WOMAN(R.string.gender_woman)
}