package com.fone.filmone.ui.common.ext

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import com.fone.filmone.ui.theme.FColor

fun Modifier.dimBackground(isDim: Boolean) = background(
    color = if (isDim) {
        FColor.DimColorThin
    } else {
        FColor.Transparent
    }
)