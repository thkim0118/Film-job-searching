package com.fone.filmone.ui.myregister

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyRegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.defaultSystemBarPadding()) {
        FTitleBar(
            titleType = TitleType.Back,
            titleText = stringResource(id = R.string.my_register_title_text),
            onBackClick = {
                navController.popBackStack()
            }
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = FColor.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = FColor.Primary
                )
            }
        ) {
            repeat(MyRegisterTab.values().size) { index ->
                val myRegisterTab = MyRegisterTab.values().find { it.index == index } ?: return@repeat
                val selected = pagerState.currentPage == index
                Tab(
                    text = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            text = stringResource(id = myRegisterTab.titleRes),
                            style = fTextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 14.textDp,
                                lineHeight = 16.8.textDp,
                                color = if (selected) {
                                    FColor.Primary
                                } else {
                                    FColor.DisablePlaceholder
                                },
                            ),
                            textAlign = TextAlign.Center
                        )
                    },
                    selected = selected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(pageCount = 2, state = pagerState) { page ->
            when (page) {
                0 -> MyRecruitmentScreen()
                1 -> MyProfileScreen()
            }
        }
    }
}

private enum class MyRegisterTab(
    @StringRes val titleRes: Int,
    val index: Int
) {
    RECRUITMENT(R.string.my_register_tab_recruitment_title, 0),
    PROFILE(R.string.my_register_tab_profile_title, 1)
}