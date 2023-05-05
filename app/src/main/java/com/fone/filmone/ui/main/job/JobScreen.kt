package com.fone.filmone.ui.main.job

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.*
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JobScreen(
    modifier: Modifier = Modifier,
    isFloatingClick: Boolean,
    onFloatingClick: (Boolean) -> Unit
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .defaultSystemBarPadding()
            .toastPadding(),
        topBar = {
            Box {
                TopAppBar(
                    backgroundColor = FColor.White,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clickableSingleWithNoRipple { },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "STAFF",
                                style = LocalTypography.current.h1(),
                                color = FColor.TextPrimary
                            )

                            Image(
                                imageVector =
                                ImageVector.vectorResource(id = R.drawable.job_tab_arrow_down),
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.job_tab_notification),
                                contentDescription = null,
                                tint = FColor.DisablePlaceholder
                            )
                        }
                    }
                }

                if (isFloatingClick) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(color = FColor.DimColorThin)
                            .clickableWithNoRipple {
                                onFloatingClick(false)
                            }
                    )
                }
            }
        },
        floatingActionButton = {
            JobFloatingButton(
                isFloatingClick = isFloatingClick,
                onFloatingClick = { isClick ->
                    onFloatingClick(isClick)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                JobHeader(
                    pagerState = pagerState,
                    coroutineScope = coroutineScope,
                )

                HorizontalPager(pageCount = JobTab.values().size, state = pagerState) { page ->
                    when (page) {
                        0 -> JobTabJobOpeningsScreen()
                        1 -> JobTabProfileScreen()
                    }
                }
            }

            if (isFloatingClick) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = FColor.DimColorThin)
                        .clickableWithNoRipple {
                            onFloatingClick(false)
                        }
                )
            }
        }
    }
}

@Composable
private fun JobFloatingButton(
    modifier: Modifier = Modifier,
    isFloatingClick: Boolean,
    onFloatingClick: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            if (isFloatingClick) {
                Column(
                    modifier = Modifier
                        .width(106.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                            .background(
                                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp),
                                color = FColor.Primary
                            )
                            .clickableSingle {  }
                            .padding(horizontal = 17.dp, vertical = 11.dp),
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.job_tab_fab_actor),
                            style = fTextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 14.textDp,
                                lineHeight = 18.textDp,
                                color = FColor.BgBase
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                    Divider(color = FColor.ColorF43663)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
                            .background(
                                shape = RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp),
                                color = FColor.Primary
                            )
                            .clickableSingle {  }
                            .padding(horizontal = 17.dp, vertical = 11.dp),
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.job_tab_fab_staff),
                            style = fTextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 14.textDp,
                                lineHeight = 18.textDp,
                                color = FColor.BgBase
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            IconButton(
                modifier = modifier
                    .clip(shape = CircleShape)
                    .background(shape = CircleShape, color = FColor.Primary),
                onClick = {
                    onFloatingClick(isFloatingClick.not())
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.job_tab_floating_image),
                    contentDescription = null
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun JobHeader(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    Column(
        modifier = modifier
            .background(color = FColor.BgGroupedBase)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        TabRow(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(6.dp))
                .background(color = FColor.White, shape = RoundedCornerShape(6.dp)),
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = FColor.White,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier.padding(3.dp)
                ) {
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .zIndex(1f)
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .fillMaxHeight()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(
                                shape = RoundedCornerShape(5.dp),
                                color = FColor.Primary
                            )
                            .padding(3.dp),
                        color = FColor.Primary
                    )
                }
            }
        ) {
            repeat(JobTab.values().size) { index ->
                val jobTab = JobTab.values().find { it.index == index } ?: return@repeat
                val selected = pagerState.currentPage == index
                Tab(
                    modifier = Modifier
                        .zIndex(2f)
                        .padding(3.dp)
                        .clip(shape = RoundedCornerShape(6.dp)),
                    selected = selected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 9.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(jobTab.titleRes),
                            style = fTextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 14.textDp,
                                lineHeight = 18.textDp,
                                color = if (selected) {
                                    FColor.BgBase
                                } else {
                                    FColor.DisablePlaceholder
                                }
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .width(103.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(shape = RoundedCornerShape(5.dp), color = FColor.White)
                    .padding(vertical = 7.dp, horizontal = 8.dp)
                    .clickableSingleWithNoRipple { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.job_tab_main_filter_recent),
                    style = LocalTypography.current.subtitle2(),
                    color = FColor.TextPrimary
                )

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    imageVector =
                    ImageVector.vectorResource(id = R.drawable.job_tab_arrow_down),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { }) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.job_tab_main_filter),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
    }
}

private enum class JobTab(
    @StringRes val titleRes: Int,
    val index: Int
) {
    JOB_OPENING(
        titleRes = R.string.job_tab_job_opening_title,
        index = 0
    ),
    PROFILE(
        titleRes = R.string.job_tab_profile_title,
        index = 1
    )
}