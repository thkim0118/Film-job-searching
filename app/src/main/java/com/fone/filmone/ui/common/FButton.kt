package com.fone.filmone.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.fShadow
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun FButton(
    modifier: Modifier = Modifier,
    title: String,
    enable: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .fShadow(
                shape = RoundedCornerShape(5.dp), spotColor = if (enable) {
                    FColor.Primary
                } else {
                    FColor.DisableBase
                }
            )
            .clip(shape = RoundedCornerShape(5.dp))
            .background(
                color = if (enable) {
                    FColor.Primary
                } else {
                    FColor.DisableBase
                }
            )
            .clickableSingle { onClick() },
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = title,
            style = LocalTypography.current.button1,
            color = FColor.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FButtonEnablePreview() {
    FilmOneTheme {
        Column(
            modifier = Modifier
                .size(120.dp)
                .padding(20.dp)
        ) {
            FButton(
                title = "다음",
                enable = true,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FButtonDisablePreview() {
    FilmOneTheme {
        Column(
            modifier = Modifier
                .size(120.dp)
                .padding(20.dp)
        ) {
            FButton(
                title = "다음",
                enable = false,
                onClick = {}
            )
        }
    }
}