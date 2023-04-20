package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.jobopenings.Category
import com.fone.filmone.data.datamodel.response.user.Job

@Keep
data class UserUpdateRequest(
    val interests: List<Category>,
    val job: Job,
    val nickname: String,
    val profileUrl: String
)