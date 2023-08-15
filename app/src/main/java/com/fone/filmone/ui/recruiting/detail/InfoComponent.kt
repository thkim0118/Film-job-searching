package com.fone.filmone.ui.recruiting.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor

@Composable
fun InfoComponent(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier
                .weight(68f),
            text = title,
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            ),
        )

        Text(
            modifier = Modifier
                .weight(268f),
            text = content,
            style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.textDp,
                lineHeight = 19.textDp,
                color = FColor.TextPrimary
            )
        )
    }
}
