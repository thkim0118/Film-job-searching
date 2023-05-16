package com.fone.filmone.data.datamodel.common.user

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.fone.filmone.R

@Keep
enum class Category(@StringRes val stringRes: Int) {
    FEATURE_FILM(R.string.category_feature_film),
    SHORT_FILM(R.string.category_short_film),
    INDEPENDENT_FILM(R.string.category_independent_film),
    WEB_DRAMA(R.string.category_web_drama),
    MOVIE(R.string.category_movie),
    OTT_DRAMA(R.string.category_ott_drama),
    YOUTUBE(R.string.category_youtube),
    VIRAL(R.string.category_viral),
    ETC(R.string.category_etc),
}