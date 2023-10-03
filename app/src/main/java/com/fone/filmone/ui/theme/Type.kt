package com.fone.filmone.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.textDp

val Pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.W400),
    Font(R.font.pretendard_medium, FontWeight.W500),
    Font(R.font.pretendard_bold, FontWeight.W700),
)

class FTypography {
    @Composable
    fun h1(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 20.textDp,
        lineHeight = 28.textDp
    )

    @Composable
    fun h2(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 19.textDp,
        lineHeight = 26.textDp
    )

    @Composable
    fun h3(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 17.textDp,
        lineHeight = 18.textDp
    )

    @Composable
    fun h4(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 16.textDp,
        lineHeight = 20.textDp
    )

    @Composable
    fun h5(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W500,
        fontSize = 16.textDp,
        lineHeight = 18.textDp
    )

    @Composable
    fun h6(): TextStyle = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 16.textDp,
        lineHeight = 18.textDp,
        letterSpacing = 0.15.textDp
    )

    @Composable
    fun subtitle1(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 15.textDp,
        lineHeight = 20.textDp
    )

    @Composable
    fun subtitle2(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W500,
        fontSize = 14.textDp,
        lineHeight = 18.textDp
    )

    @Composable
    fun b1(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 14.textDp,
        lineHeight = 19.6.textDp
    )

    @Composable
    fun b2(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W400,
        fontSize = 14.textDp,
        lineHeight = 20.textDp
    )

    @Composable
    fun b3(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W500,
        fontSize = 13.textDp,
        lineHeight = 16.textDp
    )

    @Composable
    fun b4(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W500,
        fontSize = 12.textDp,
        lineHeight = 18.textDp
    )

    @Composable
    fun button1(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W500,
        fontSize = 16.textDp,
        lineHeight = 16.textDp
    )

    @Composable
    fun button2(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W400,
        fontSize = 14.textDp,
        lineHeight = 14.textDp,
    )

    @Composable
    fun label(): TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W400,
        fontSize = 12.textDp,
        lineHeight = 17.textDp,
    )
}

internal val LocalTypography = staticCompositionLocalOf { FTypography() }
