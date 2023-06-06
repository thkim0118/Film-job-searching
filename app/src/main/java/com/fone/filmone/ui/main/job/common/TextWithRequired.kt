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
import com.fone.filmone.ui.theme.Pretendard

@Composable
fun TextWithRequired(
    modifier: Modifier = Modifier,
    title: String,
    isRequired: Boolean
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.W500,
                    color = FColor.TextPrimary
                )
            ) {
                append(title)
            }
            if (isRequired) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W500,
                        color = FColor.Error
                    )
                ) {
                    append(" *")
                }
            }
        },
        fontFamily = Pretendard,
        fontSize = 15.textDp,
        lineHeight = 20.textDp,
    )
}
