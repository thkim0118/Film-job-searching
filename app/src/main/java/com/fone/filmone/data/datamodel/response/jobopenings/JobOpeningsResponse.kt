package com.fone.filmone.data.datamodel.response.jobopenings

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.JobOpenings

@Keep
data class JobOpeningsResponse(
    val jobOpenings: JobOpenings
)