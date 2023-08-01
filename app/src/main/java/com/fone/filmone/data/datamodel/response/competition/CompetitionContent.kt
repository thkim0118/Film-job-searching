package com.fone.filmone.data.datamodel.response.competition

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.Type

@Keep
data class CompetitionContent(
    val agency: String,
    val competitionPrizes: List<CompetitionPrize>,
    val dday: String,
    val details: String,
    val endDate: String,
    val id: Int,
    val imageUrl: String,
    val isScrap: Boolean,
    val scrapCount: Int,
    val showStartDate: String,
    val startDate: String,
    val submitEndDate: String,
    val submitStartDate: String,
    val title: String,
    val viewCount: Int,
    val type: Type,
)