package com.fone.filmone.data.datamodel.common.jobopenings

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender

@Keep
data class JobOpeningContent(
    val id: Int,
    val title: String,
    val categories: List<Category>,
    val deadline: String,
    val casting: String,
    val numberOfRecruits: Int,
    val gender: Gender,
    val ageMax: Int,
    val ageMin: Int,
    val career: Career,
    val type: Type,
    val domains: List<Domain>,
    val viewCount: Int,
    val scrapCount: Int,
    val work: Work,
    val isScrap: Boolean,
    val dday: String
)
