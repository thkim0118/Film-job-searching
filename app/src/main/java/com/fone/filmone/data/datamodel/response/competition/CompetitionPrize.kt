package com.fone.filmone.data.datamodel.response.competition

import androidx.annotation.Keep

@Keep
data class CompetitionPrize(
    val competitionId: Int,
    val id: Int,
    val prizeMoney: String,
    val ranking: String
)