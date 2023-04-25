package com.fone.filmone.data.datamodel.response.profiles

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.common.user.Career
import com.fone.filmone.data.datamodel.response.common.user.Category
import com.fone.filmone.data.datamodel.response.common.user.Domain
import com.fone.filmone.data.datamodel.response.common.user.Gender

@Keep
data class Content(
    val age: Int,
    val birthday: String,
    val career: Career,
    val categories: List<Category>,
    val details: String,
    val domains: List<Domain>,
    val email: String,
    val gender: Gender,
    val height: Int,
    val hookingComment: String,
    val id: Int,
    val isWant: Boolean,
    val name: String,
    val profileUrl: String,
    val profileUrls: List<String>,
    val sns: String,
    val specialty: String,
    val viewCount: Int,
    val weight: Int
)