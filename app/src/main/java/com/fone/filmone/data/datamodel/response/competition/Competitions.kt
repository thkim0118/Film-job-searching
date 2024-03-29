package com.fone.filmone.data.datamodel.response.competition

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.paging.Pageable
import com.fone.filmone.data.datamodel.common.paging.Sort

@Keep
data class Competitions(
    val content: List<CompetitionContent>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort
)
