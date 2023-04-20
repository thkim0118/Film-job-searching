package com.fone.filmone.ui.scrap

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrapScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Column(modifier = modifier.defaultSystemBarPadding()) {
        FTitleBar(
            titleType = TitleType.Back,
            titleText = stringResource(id = R.string.scrap_title_text),
            onBackClick = {
                navController.popBackStack()
            }
        )
        
        HorizontalPager(pageCount = 2) { page ->
            when (page) {
                0 -> JobOpeningScreen()
                1 -> CompetitionsScreen()
            }
        }
    }
}
