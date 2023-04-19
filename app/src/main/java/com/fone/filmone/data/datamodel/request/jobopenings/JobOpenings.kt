package com.fone.filmone.data.datamodel.request.jobopenings

import androidx.annotation.Keep

@Keep
data class JobOpenings(
    val content: List<Content>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort
)