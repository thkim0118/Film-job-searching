package com.fone.filmone.data.datamodel.common.jobopenings

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.paging.Pageable
import com.fone.filmone.data.datamodel.common.paging.Sort

@Keep
data class JobOpenings(
    val content: List<JobOpeningContent>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort,
    val totalPages: Int,
    val totalElements: Int,
)