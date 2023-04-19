package com.fone.filmone.data.datamodel.request.jobopenings

import androidx.annotation.Keep

@Keep
data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)