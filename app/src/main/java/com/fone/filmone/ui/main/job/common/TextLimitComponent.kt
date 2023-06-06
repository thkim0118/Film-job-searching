package com.fone.filmone.ui.main.job.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.theme.FColor

@Composable
fun TextLimitComponent(
    modifier: Modifier = Modifier,
    currentTextSize: Int,
    maxTextSize: Int,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(color = FColor.TextSecondary)
            ) {
                append(currentTextSize.toString())
            }

            withStyle(
                style = SpanStyle(color = FColor.DisableBase)
            ) {
                append("/")
            }

            withStyle(
                style = SpanStyle(color = FColor.DisableBase)
            ) {
                append(maxTextSize.toString())
            }
        },
        fontWeight = FontWeight.W400,
        fontSize = 12.textDp,
        lineHeight = 17.textDp
    )
}
