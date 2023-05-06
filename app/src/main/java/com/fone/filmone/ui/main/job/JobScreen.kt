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
import androidx.hilt.navigation.compose.hiltViewModel
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
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
    onJobOpeningsFilterClick: () -> Unit,
    onProfileFilterClick: () -> Unit,
    viewModel: JobScreenViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        JobHeader(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            type = uiState.type,
            onTypeClick = viewModel::updateType,
            currentJobFilter = uiState.currentJobFilter,
            onJobFilterClick = {
                when (uiState.currentJobFilter) { // TODO 디버깅
                    is JobFilter.JobOpenings -> onJobOpeningsFilterClick()
                    is JobFilter.Profile -> onProfileFilterClick()
                }
            },
            onUpdateCurrentJobFilter = viewModel::updateCurrentJobFilter
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
    type: Type,
    onTypeClick: (Type) -> Unit,
    currentJobFilter: JobFilter,
    onJobFilterClick: () -> Unit,
    onUpdateCurrentJobFilter: (JobTab) -> Unit,
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
            },
            type = type
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
            coroutineScope = coroutineScope,
            currentJobFilter = currentJobFilter,
            onJobFilterClick = onJobFilterClick,
            onUpdateCurrentJobFilter = onUpdateCurrentJobFilter
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
            JobTitleFilterBox(
                currentType = type,
                onTypeClick = {
                    isTitleFilterClick = false
                    onTypeClick(it)
                }
            )
        }
    }
}

@Composable
private fun JobTitleContents(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    type: Type
) {
    Row(
        modifier = modifier
            .clickableWithNoRipple { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = type.name,
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
    currentJobFilter: JobFilter,
    onUpdateCurrentJobFilter: (JobTab) -> Unit,
    onJobFilterClick: () -> Unit
) {
    JobTab.values().find { it.index == pagerState.currentPage }?.let { jobTab ->
        onUpdateCurrentJobFilter(jobTab)
    }

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
                    .clickableSingleWithNoRipple {
                        onJobFilterClick()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(currentJobFilter.currentFilterType.titleRes),
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
    modifier: Modifier = Modifier,
    currentType: Type,
    onTypeClick: (Type) -> Unit
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
                    .clickableSingle {
                        onTypeClick(Type.ACTOR)
                    }
                    .padding(vertical = 13.dp, horizontal = 13.dp),
                text = Type.ACTOR.name,
                style = fTextStyle(
                    fontWeight = if (currentType == Type.ACTOR) {
                        FontWeight.W700
                    } else {
                        FontWeight.W500
                    },
                    fontSize = 17.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.TextPrimary
                ),
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableSingle {
                        onTypeClick(Type.STAFF)
                    }
                    .padding(vertical = 13.dp, horizontal = 13.dp),
                text = Type.STAFF.name,
                style = fTextStyle(
                    fontWeight = if (currentType == Type.STAFF) {
                        FontWeight.W700
                    } else {
                        FontWeight.W500
                    },
                    fontSize = 17.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.TextPrimary
                ),
                textAlign = TextAlign.Start
            )
        }
    }
}

enum class JobTab(
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