package com.fone.filmone.ui.common.empty

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.empty_image),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = title,
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary
        )
    }
}
