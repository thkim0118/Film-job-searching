package com.fone.filmone.data.datamodel.response.jobopenings.detail

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.JobOpening

@Keep
data class JobOpeningResponse(
    val jobOpening: JobOpening
)