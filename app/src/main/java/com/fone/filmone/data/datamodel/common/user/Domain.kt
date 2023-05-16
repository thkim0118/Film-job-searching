package com.fone.filmone.data.datamodel.common.user

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.fone.filmone.R

@Keep
enum class Domain(@StringRes val stringRes: Int) {
    PLANNING(R.string.job_opening_domain_planning),
    SCENARIO(R.string.job_opening_domain_scenario),
    DIRECTOR(R.string.job_opening_domain_director),
    FILMING(R.string.job_opening_domain_filming),
    GAFFER(R.string.job_opening_domain_gaffer),
    RECORD(R.string.job_opening_domain_record),
    PAINTING(R.string.job_opening_domain_painting),
    ART(R.string.job_opening_domain_art),
    MAKE_UP(R.string.job_opening_domain_make_up),
    EDIT(R.string.job_opening_domain_edit),
    MUSIC(R.string.job_opening_domain_music),
    PICTURE(R.string.job_opening_domain_picture),
    ETC(R.string.job_opening_domain_etc)
}