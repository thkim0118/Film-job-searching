package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.fone.filmone.R

@Keep
enum class LoginType(@StringRes val titleRes: Int) {
    APPLE(R.string.apple),
    GOOGLE(R.string.google),
    KAKAO(R.string.kakao),
    NAVER(R.string.naver),
    PASSWORD(-1),
}