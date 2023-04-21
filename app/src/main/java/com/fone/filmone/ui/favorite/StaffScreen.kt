package com.fone.filmone.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun StaffScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (listOf<String>().isNotEmpty()) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {

            }
        } else {
            EmptyScreen(modifier = Modifier.align(Alignment.Center))
        }
    }
}


@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.scrap_empty_image),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.favorite_empty_title),
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary
        )
    }
}