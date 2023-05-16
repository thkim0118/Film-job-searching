package com.fone.filmone.data.datamodel.common.user

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.fone.filmone.R

@Keep
enum class Career(@StringRes titleRes: Int) {
    NEWCOMER(R.string.job_opening_career_newcomer),
    LESS_THAN_1YEARS(R.string.job_opening_career_1year),
    LESS_THAN_3YEARS(R.string.job_opening_career_3year),
    LESS_THAN_6YEARS(R.string.job_opening_career_6year),
    LESS_THAN_10YEARS(R.string.job_opening_career_10year),
    MORE_THAN_10YEARS(R.string.job_opening_career_10year_more),
    IRRELEVANT(R.string.job_opening_career_irrelevant),
}