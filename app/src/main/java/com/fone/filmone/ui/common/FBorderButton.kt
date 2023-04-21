package com.fone.filmone.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme

@Composable
fun FBorderButton(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .border(
                width = 1.dp,
                color = if (enable) {
                    FColor.PrimaryLighten
                } else {
                    FColor.Divider1
                },
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 13.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.textDp,
                lineHeight = 14.textDp,
                color = if (enable) {
                    FColor.Primary
                } else {
                    FColor.DisablePlaceholder
                }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FBorderButtonEnablePreview() {
    FilmOneTheme {
        FBorderButton(text = "중복확인", enable = true, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun FBorderButtonDisablePreview() {
    FilmOneTheme {
        FBorderButton(text = "중복확인", enable = false, onClick = {})
    }
}