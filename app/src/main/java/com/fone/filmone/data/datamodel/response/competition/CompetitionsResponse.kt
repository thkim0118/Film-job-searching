package com.fone.filmone.data.datamodel.response.competition

import androidx.annotation.Keep

@Keep
data class CompetitionsResponse(
    val competitions: Competitions,
    val totalCount: Int
)
