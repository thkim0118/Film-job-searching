package com.fone.filmone.ui.main.job.recruiting.article.actor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor

@Composable
fun ActorRecruitingArticleScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
    ) {
        TitleComponent(
            onBackClick = {},
            onMoreImageClick = {}
        )


    }
}

@Composable
private fun TitleComponent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onMoreImageClick: () -> Unit
) {
    FTitleBar(
        modifier = modifier,
        titleText = stringResource(id = R.string.article_actor_title_text),
        leading = {
            Image(
                modifier = Modifier
                    .clickableSingleWithNoRipple { onBackClick() },
                imageVector = ImageVector.vectorResource(id = R.drawable.title_bar_back),
                contentDescription = null
            )
        },
        action = {
            Image(
                modifier = Modifier
                    .clickableSingleWithNoRipple { onMoreImageClick() },
                imageVector = ImageVector.vectorResource(id = R.drawable.actor_article_more_vertical),
                contentDescription = null
            )
        }
    )
}
