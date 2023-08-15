package com.fone.filmone.data.datamodel.response.jobopenings.detail

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.JobOpeningContent

@Keep
data class JobOpeningResponse(
    val jobOpening: JobOpeningContent
)
