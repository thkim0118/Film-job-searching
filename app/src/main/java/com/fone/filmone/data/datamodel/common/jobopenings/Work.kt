package com.fone.filmone.data.datamodel.common.jobopenings

import androidx.annotation.Keep

@Keep
data class Work(
    val produce: String,
    val workTitle: String,
    val director: String,
    val genre: String,
    val logline: String?,
    val location: String?,
    val period: String?,
    val pay: String?,
    val details: String,
    val manager: String,
    val email: String,
)