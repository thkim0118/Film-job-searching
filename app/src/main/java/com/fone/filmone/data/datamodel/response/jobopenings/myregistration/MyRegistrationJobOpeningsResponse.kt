package com.fone.filmone.data.datamodel.response.jobopenings.myregistration

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.JobOpenings

@Keep
data class MyRegistrationJobOpeningsResponse(
    val jobOpenings: JobOpenings
)