package com.fone.filmone.data.datamodel.request.profile

import androidx.annotation.Keep

@Keep
data class ProfileRegisterRequest(
    val birthday: String,
    val career: String,
    val careerDetail: String,
    val categories: List<String>,
    val details: String,
    val domains: List<String>?,
    val email: String,
    val gender: String,
    val height: Int?,
    val hookingComment: String,
    val name: String,
    val profileUrl: String,
    val profileUrls: List<String>,
    val sns: String,
    val specialty: String,
    val type: String,
    val weight: Int?,
)
