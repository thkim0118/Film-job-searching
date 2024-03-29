package com.fone.filmone.domain.model.jobopenings

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender

@Keep
data class JobTabFilterVo(
    val ageMax: Int,
    val ageMin: Int,
    val categories: List<Category>?,
    val domains: List<Domain>?,
    val genders: List<Gender>?,
    val page: Int = 0,
    val size: Int = 20,
    val sort: String,
    val type: Type,
)
