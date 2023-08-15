package com.fone.filmone.data.datamodel.response.home

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.competition.Competitions
import com.google.gson.annotations.SerializedName

@Keep
data class Competition(
    @SerializedName("data")
    val competitions: Competitions,
    val subTitle: String,
    val title: String
)
