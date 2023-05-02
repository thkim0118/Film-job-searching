package com.fone.filmone.data.datamodel.response.common.jobopenings

import androidx.annotation.Keep

@Keep
data class Work(
    val details: String,
    val director: String,
    val email: String,
    val genre: String,
    val location: String,
    val logline: String,
    val manager: String,
    val pay: String,
    val period: String,
    val produce: String,
    val workTitle: String
)