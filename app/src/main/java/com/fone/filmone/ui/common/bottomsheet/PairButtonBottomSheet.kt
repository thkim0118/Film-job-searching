package com.fone.filmone.ui.common.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun PairButtonBottomSheet(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .background(shape = RoundedCornerShape(5.dp), color = FColor.White)
            .padding(horizontal = 16.dp)
    ) {
        content()

        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(shape = RoundedCornerShape(5.dp), color = FColor.Primary)
                    .clickableSingle { onLeftButtonClick() }
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.no),
                    style = LocalTypography.current.button1(),
                    color = FColor.White
                )
            }

            Spacer(modifier = Modifier.width(7.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(shape = RoundedCornerShape(5.dp), color = FColor.BgGroupedBase)
                    .clickableSingle { onRightButtonClick() }
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.yes),
                    style = LocalTypography.current.button1(),
                    color = FColor.TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}