package com.fone.filmone.data.datamodel.response.profiles

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.common.paging.Pageable
import com.fone.filmone.data.datamodel.response.common.paging.Sort

@Keep
data class Profiles(
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