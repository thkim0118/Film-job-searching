package com.fone.filmone.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme

@Composable
fun FTitleBar(
    modifier: Modifier = Modifier,
    titleText: String = "",
    titleType: TitleType,
    onBackClick: () -> Unit = {},
    onCloseClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        Image(
            modifier = Modifier
                .alpha(
                    if (titleType is TitleType.Back) {
                        1f
                    } else {
                        0f
                    }
                )
                .clickableSingleWithNoRipple { onBackClick() },
            imageVector = ImageVector.vectorResource(id = R.drawable.title_bar_back),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(11.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = titleText,
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 19.textDp,
                lineHeight = 26.textDp,
                color = FColor.TextPrimary
            ),
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.width(11.dp))

        Image(
            modifier = Modifier
                .alpha(
                    if (titleType is TitleType.Close) {
                        1f
                    } else {
                        0f
                    }
                )
                .clickableSingleWithNoRipple { onCloseClick() },
            imageVector = ImageVector.vectorResource(id = R.drawable.title_bar_close),
            contentDescription = null
        )


        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun FTitleBar(
    modifier: Modifier = Modifier,
    titleText: String = "",
    leading: @Composable () -> Unit,
    action: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        leading()

        Spacer(modifier = Modifier.width(11.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = titleText,
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 19.textDp,
                lineHeight = 26.textDp,
                color = FColor.TextPrimary
            ),
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.width(11.dp))

        action()

        Spacer(modifier = Modifier.width(16.dp))
    }
}


sealed interface TitleType {
    object Back : TitleType
    object Close : TitleType
}

@Preview(showBackground = true)
@Composable
private fun TitleBarBackTypePreview() {
    FilmOneTheme {
        FTitleBar(
            titleText = "뒤로가기",
            titleType = TitleType.Back
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleBarCloseTypePreview() {
    FilmOneTheme {
        FTitleBar(
            titleText = "닫기",
            titleType = TitleType.Close
        )
    }
}