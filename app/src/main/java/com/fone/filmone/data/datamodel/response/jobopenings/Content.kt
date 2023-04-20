package com.fone.filmone.data.datamodel.response.jobopenings

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.user.Gender

@Keep
data class Content(
    val ageMax: Int,
    val ageMin: Int,
    val career: Career,
    val casting: String,
    val categories: List<Category>,
    val dday: String,
    val deadline: String,
    val domains: List<Domain>,
    val gender: Gender,
    val id: Int,
    val isScrap: Boolean,
    val numberOfRecruits: Int,
    val scrapCount: Int,
    val title: String,
    val type: Type,
    val viewCount: Int,
    val work: Work
)