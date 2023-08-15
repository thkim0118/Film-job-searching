package com.fone.filmone.ui.main.job.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor

@Composable
fun TagComponent(
    modifier: Modifier = Modifier,
    title: String,
    enable: Boolean,
    onClick: () -> Unit = {},
    clickable: Boolean = true
) {
    val tagModifier = modifier
        .clip(shape = RoundedCornerShape(100.dp))
        .background(
            shape = RoundedCornerShape(100.dp),
            color = if (enable) {
                FColor.Secondary1
            } else {
                FColor.DisableBase
            }
        )
        .clickable {
            if (clickable) {
                onClick()
            }
        }

    Box(
        modifier = tagModifier
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 15.dp),
            text = title,
            style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 12.textDp,
                lineHeight = 12.textDp,
                color = FColor.BgBase
            )
        )
    }
}
