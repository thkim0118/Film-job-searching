package com.fone.filmone.ui.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.fone.filmone.ui.theme.Pretendard

fun fTextStyle(
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    color: Color,
    fontFamily: FontFamily = Pretendard,
): TextStyle {
    return TextStyle(
        fontWeight = fontWeight,
        fontSize = fontSize,
        lineHeight = lineHeight,
        color = color,
        fontFamily = fontFamily,
    )
}