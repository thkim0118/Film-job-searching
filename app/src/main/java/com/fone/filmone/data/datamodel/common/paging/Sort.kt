package com.fone.filmone.data.datamodel.common.paging

import androidx.annotation.Keep

@Keep
data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)