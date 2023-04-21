package com.fone.filmone.ui.myregister

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun MyRecruitmentScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (listOf<String>().isNotEmpty()) {
        } else {
            EmptyScreen(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.my_register_recruitment_empty_title),
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.my_register_recruitment_empty_subtitle_actor),
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary,
                textDecoration = TextDecoration.Underline
            )

            Text(
                text = " | ",
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary
            )

            Text(
                text = stringResource(id = R.string.my_register_recruitment_empty_subtitle_staff),
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary,
                textDecoration = TextDecoration.Underline
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}