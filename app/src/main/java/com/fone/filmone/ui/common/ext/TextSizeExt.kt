package com.fone.filmone.ui.common.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
private fun Double.textDp(density: Density): TextUnit = with(density) {
    this@textDp.dp.toSp()
}

val Double.textDp: TextUnit
    @Composable get() = this.textDp(density = LocalDensity.current)

@Composable
private fun Int.textDp(density: Density): TextUnit = with(density) {
    this@textDp.dp.toSp()
}

val Int.textDp: TextUnit
    @Composable get() = this.textDp(density = LocalDensity.current)
