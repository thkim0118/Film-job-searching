package com.fone.filmone.domain.model.jobopenings

import androidx.annotation.StringRes
import com.fone.filmone.R

enum class JobType(@StringRes val stringRes: Int) {
    PART(R.string.job_opening_job_type_part_title),
    Field(R.string.job_opening_job_type_field_title)
}