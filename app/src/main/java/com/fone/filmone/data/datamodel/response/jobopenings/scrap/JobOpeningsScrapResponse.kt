package com.fone.filmone.data.datamodel.response.jobopenings.scrap

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.common.jobopenings.JobOpenings

@Keep
data class JobOpeningsScrapResponse(
    val jobOpenings: JobOpenings
)