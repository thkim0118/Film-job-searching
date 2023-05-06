package com.fone.filmone.ui.main.job

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.*
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.shadow.BottomShadow
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JobScreen(
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun JobHeader(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    var isTitleFilterClick by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (titleBar, titleContents, titleAlarmImage, titleFilterBox, titleShadow, filterHeader) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
                .constrainAs(titleBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        JobTitleContents(
            modifier = Modifier
                .constrainAs(titleContents) {
                    top.linkTo(titleBar.top)
                    bottom.linkTo(titleBar.bottom)
                    start.linkTo(titleBar.start, margin = 16.dp)
                },
            onClick = {
                isTitleFilterClick = isTitleFilterClick.not()
            }
        )

        JobTitleAlarmImage(
            modifier = Modifier
                .constrainAs(titleAlarmImage) {
                    top.linkTo(titleBar.top)
                    bottom.linkTo(titleBar.bottom)
                    end.linkTo(titleBar.end, margin = 16.dp)
                }
        )

        JobFilterHeader(
            modifier = Modifier
                .constrainAs(filterHeader) {
                    top.linkTo(titleBar.bottom)
                },
            pagerState = pagerState,
            coroutineScope = coroutineScope
        )

        BottomShadow(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            shadowReference = titleShadow,
            targetReference = titleBar
        )

        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(titleFilterBox) {
                    start.linkTo(titleContents.start)
                    top.linkTo(titleContents.bottom)
                },
            enter = fadeIn(),
            exit = fadeOut(),
            visible = isTitleFilterClick
        ) {
            JobTitleFilterBox()
        }
    }
}

@Composable
private fun JobTitleContents(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickableWithNoRipple { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "STAFF",
            style = LocalTypography.current.h1(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.width(9.dp))

        Image(
            imageVector =
            ImageVector.vectorResource(id = R.drawable.job_tab_arrow_down),
            contentDescription = null
        )
    }
}

@Composable
private fun JobTitleAlarmImage(
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = { }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.job_tab_notification),
            contentDescription = null,
            tint = FColor.DisablePlaceholder
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun JobFilterHeader(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
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

@Composable
private fun JobTitleFilterBox(
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(5.dp)

    Card(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = shape
            ),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = modifier
                .clip(shape = shape)
                .background(shape = shape, color = FColor.BgBase)
                .width(149.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableSingle { }
                    .padding(vertical = 13.dp, horizontal = 13.dp),
                text = stringResource(id = R.string.job_tab_title_actor),
                style = fTextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 17.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.TextPrimary
                ),
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableSingle { }
                    .padding(vertical = 13.dp, horizontal = 13.dp),
                text = stringResource(id = R.string.job_tab_title_staff),
                style = fTextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 17.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.TextPrimary
                ),
                textAlign = TextAlign.Start
            )
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
                            .clickableSingle { }
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
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomStart = 6.dp,
                                    bottomEnd = 6.dp
                                )
                            )
                            .background(
                                shape = RoundedCornerShape(
                                    bottomStart = 6.dp,
                                    bottomEnd = 6.dp
                                ),
                                color = FColor.Primary
                            )
                            .clickableSingle { }
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