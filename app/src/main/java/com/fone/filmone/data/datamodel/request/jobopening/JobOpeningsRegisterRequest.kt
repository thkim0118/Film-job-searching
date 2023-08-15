package com.fone.filmone.data.datamodel.request.jobopening

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.jobopenings.Work
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Gender

@Keep
data class JobOpeningsRegisterRequest(
    val ageMax: Int,
    val ageMin: Int,
    val career: Career,
    val casting: String?,
    val categories: List<String>,
    val deadline: String,
    val domains: List<String>?,
    val gender: Gender,
    val numberOfRecruits: Int,
    val title: String,
    val type: Type,
    val work: Work
)
