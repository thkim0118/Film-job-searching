package com.fone.filmone.data.datamodel.response.competition

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.common.Pageable
import com.fone.filmone.data.datamodel.response.common.Sort

@Keep
data class Competitions(
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