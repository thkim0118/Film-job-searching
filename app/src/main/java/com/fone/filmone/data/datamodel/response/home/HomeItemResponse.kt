package com.fone.filmone.data.datamodel.response.home

import androidx.annotation.Keep

@Keep
data class HomeItemResponse(
    val competition: Competition,
    val jobOpening: JobOpening,
    val order: List<HomeOrder>,
    val profile: Profile
)
