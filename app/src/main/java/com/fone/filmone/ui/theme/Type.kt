package com.fone.filmone.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fone.filmone.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.W400),
    Font(R.font.pretendard_medium, FontWeight.W500),
    Font(R.font.pretendard_bold, FontWeight.W700),
)

@Immutable
class FTypography internal constructor(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,
    val subtitle1: TextStyle,
    val subtitle2: TextStyle,
    val b1: TextStyle,
    val b2: TextStyle,
    val b3: TextStyle,
    val button1: TextStyle,
    val button2: TextStyle,
    val label: TextStyle
) {
    constructor(
        defaultFontFamily: FontFamily = Pretendard,
        h1: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
            lineHeight = 28.sp
        ),
        h2: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W700,
            fontSize = 19.sp,
            lineHeight = 26.sp
        ),
        h3: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W500,
            fontSize = 17.sp,
            lineHeight = 18.sp
        ),
        h4: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            lineHeight = 19.2.sp
        ),
        h5: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            lineHeight = 19.2.sp
        ),
        h6: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            letterSpacing = 0.15.sp
        ),
        subtitle1: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W500,
            fontSize = 15.sp,
            lineHeight = 20.sp
        ),
        subtitle2: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 18.sp
        ),
        b1: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 19.sp
        ),
        b2: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W500,
            fontSize = 13.sp,
            lineHeight = 16.sp
        ),
        b3: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
        button1: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            lineHeight = 16.sp
        ),
        button2: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 14.sp,
        ),
        label: TextStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            lineHeight = 17.sp,
        )
    ) : this(
        h1 = h1.withDefaultFontFamily(defaultFontFamily),
        h2 = h2.withDefaultFontFamily(defaultFontFamily),
        h3 = h3.withDefaultFontFamily(defaultFontFamily),
        h4 = h4.withDefaultFontFamily(defaultFontFamily),
        h5 = h5.withDefaultFontFamily(defaultFontFamily),
        h6 = h6.withDefaultFontFamily(defaultFontFamily),
        subtitle1 = subtitle1.withDefaultFontFamily(defaultFontFamily),
        subtitle2 = subtitle2.withDefaultFontFamily(defaultFontFamily),
        b1 = b1.withDefaultFontFamily(defaultFontFamily),
        b2 = b2.withDefaultFontFamily(defaultFontFamily),
        b3 = b3.withDefaultFontFamily(defaultFontFamily),
        button1 = button1.withDefaultFontFamily(defaultFontFamily),
        button2 = button2.withDefaultFontFamily(defaultFontFamily),
        label = label.withDefaultFontFamily(defaultFontFamily)
    )

    /**
     * Returns a copy of this Typography, optionally overriding some of the values.
     */
    fun copy(
        h1: TextStyle = this.h1,
        h2: TextStyle = this.h2,
        h3: TextStyle = this.h3,
        h4: TextStyle = this.h4,
        h5: TextStyle = this.h5,
        h6: TextStyle = this.h6,
        subtitle1: TextStyle = this.subtitle1,
        subtitle2: TextStyle = this.subtitle2,
        b1: TextStyle = this.b1,
        b2: TextStyle = this.b2,
        b3: TextStyle = this.b3,
        button1: TextStyle = this.button1,
        button2: TextStyle = this.button2,
        label: TextStyle = this.label
    ): FTypography = FTypography(
        h1 = h1,
        h2 = h2,
        h3 = h3,
        h4 = h4,
        h5 = h5,
        h6 = h6,
        subtitle1 = subtitle1,
        subtitle2 = subtitle2,
        b1 = b1,
        b2 = b2,
        b3 = b3,
        button1 = button1,
        button2 = button2,
        label = label
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FTypography) return false

        if (h1 != other.h1) return false
        if (h2 != other.h2) return false
        if (h3 != other.h3) return false
        if (h4 != other.h4) return false
        if (h5 != other.h5) return false
        if (h6 != other.h6) return false
        if (subtitle1 != other.subtitle1) return false
        if (subtitle2 != other.subtitle2) return false
        if (b1 != other.b1) return false
        if (b2 != other.b2) return false
        if (b3 != other.b3) return false
        if (button1 != other.button1) return false
        if (button2 != other.button2) return false
        if (label != other.label) return false

        return true
    }

    override fun hashCode(): Int {
        var result = h1.hashCode()
        result = 31 * result + h2.hashCode()
        result = 31 * result + h3.hashCode()
        result = 31 * result + h4.hashCode()
        result = 31 * result + h5.hashCode()
        result = 31 * result + h6.hashCode()
        result = 31 * result + subtitle1.hashCode()
        result = 31 * result + subtitle2.hashCode()
        result = 31 * result + b1.hashCode()
        result = 31 * result + b2.hashCode()
        result = 31 * result + b3.hashCode()
        result = 31 * result + button1.hashCode()
        result = 31 * result + button2.hashCode()
        result = 31 * result + label.hashCode()
        return result
    }

    override fun toString(): String {
        return "FTypography(h1=$h1, h2=$h2, h3=$h3, h4=$h4, h5=$h5, h6=$h6, " +
                "subtitle1=$subtitle1, subtitle2=$subtitle2, " +
                "b1=$b1, b2=$b2, b3=$b3, button1=$button1, button2=$button2, label=$label)"
    }
}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val LocalTypography = staticCompositionLocalOf { FTypography() }
