package com.fone.filmone.data.datamodel.response.home

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.JobOpenings
import com.google.gson.annotations.SerializedName

@Keep
data class JobOpening(
    @SerializedName("data")
    val jobOpenings: JobOpenings,
    val subTitle: String,
    val title: String
)
