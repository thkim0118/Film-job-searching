package com.fone.filmone.ui.common.ext

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier

fun Modifier.defaultSystemBarPadding() = statusBarsPadding().navigationBarsPadding()
