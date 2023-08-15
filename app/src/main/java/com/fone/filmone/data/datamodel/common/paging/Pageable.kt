package com.fone.filmone.data.datamodel.common.paging

import androidx.annotation.Keep

@Keep
data class Pageable(
    val sort: Sort,
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)
