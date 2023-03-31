package com.fone.filmone.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme

@Composable
fun FRadioButton(
    modifier: Modifier = Modifier,
    enable: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(16.dp)
            .clip(shape = CircleShape)
            .border(
                width = 1.dp,
                color = if (enable) {
                    FColor.Transparent
                } else {
                    FColor.DisableBase
                },
                shape = CircleShape
            )
            .background(
                color = if (enable) {
                    FColor.Primary
                } else {
                    FColor.Transparent
                },
                shape = CircleShape
            )
            .clickableSingle { onClick.invoke() }
    ) {
        if (enable) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.radio_button_check),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FRadioButtonEnablePreview() {
    FilmOneTheme {
        FRadioButton(enable = true)
    }
}

@Preview(showBackground = true)
@Composable
fun FRadioButtonDisablePreview() {
    FilmOneTheme {
        FRadioButton(enable = false)
    }
}