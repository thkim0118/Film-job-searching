package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.user.Interests
import com.fone.filmone.data.datamodel.response.user.Job

@Keep
data class UserUpdateRequest(
    val interests: List<Interests>,
    val job: Job,
    val nickname: String,
    val profileUrl: String
)