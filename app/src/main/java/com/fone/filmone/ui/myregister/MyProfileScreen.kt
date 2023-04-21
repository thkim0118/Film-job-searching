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
fun MyProfileScreen(
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
            text = stringResource(id = R.string.my_register_profile_empty_title),
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.my_register_profile_empty_subtitle),
            style = LocalTypography.current.subtitle2(),
            color = FColor.Primary,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
    }
}