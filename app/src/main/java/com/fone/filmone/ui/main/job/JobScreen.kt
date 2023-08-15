package com.fone.filmone.ui.main.job

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.ui.common.empty.EmptyScreen
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.shadow.BottomShadow
import com.fone.filmone.ui.main.JobSorting
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JobScreen(
    modifier: Modifier = Modifier,
    currentJobSorting: JobSorting,
    userType: Type,
    initialJobTab: JobTab = JobTab.JOB_OPENING,
    onUpdateCurrentJobSorting: (JobTab) -> Unit,
    onJobOpeningsFilterClick: () -> Unit,
    onProfileFilterClick: () -> Unit,
    onJobPageChanged: (JobTab) -> Unit,
    onUpdateUserType: (Type) -> Unit,
    viewModel: JobScreenSharedViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(initialPage = initialJobTab.index)
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = userType) {
        viewModel.initUserType(userType)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        JobHeader(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            type = uiState.type ?: userType,
            onTypeClick = {
                viewModel.updateType(it)
                onUpdateUserType(it)
            },
            currentJobSorting = currentJobSorting,
            onJobSortingClick = {
                when (currentJobSorting) {
                    is JobSorting.JobOpenings -> onJobOpeningsFilterClick()
                    is JobSorting.Profile -> onProfileFilterClick()
                }
            },
            onUpdateCurrentJobSorting = onUpdateCurrentJobSorting,
            onFilterClick = {
                val route = when (uiState.type ?: userType) {
                    Type.ACTOR -> FOneDestinations.ActorFilter.route
                    Type.STAFF -> FOneDestinations.StaffFilter.route
                }

                FOneNavigator.navigateTo(navDestinationState = NavDestinationState(route = route))
            }
        )

        HorizontalPager(pageCount = JobTab.values().size, state = pagerState) { page ->
            when (page) {
                JobTab.JOB_OPENING.index -> {
                    onJobPageChanged(JobTab.JOB_OPENING)

                    if (uiState.jobOpeningsUiModel.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = FColor.BgGroupedBase)
                        ) {
                            EmptyScreen(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .background(color = FColor.BgGroupedBase),
                                title = stringResource(id = R.string.job_tab_job_opening_empty_title)
                            )
                        }
                    } else {
                        JobTabJobOpeningsComponent(
                            jobTabJobOpeningUiModels = uiState.jobOpeningsUiModel,
                            onLastItemVisible = {
                            }
                        )
                    }
                }

                JobTab.PROFILE.index -> {
                    onJobPageChanged(JobTab.PROFILE)

                    if (uiState.profileUiModels.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = FColor.BgGroupedBase)
                        ) {
                            EmptyScreen(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                title = stringResource(id = R.string.job_tab_profile_empty_title)
                            )
                        }
                    } else {
                        JobTabProfileComponent(
                            jobTabProfilesUiModels = uiState.profileUiModels
                        )
                    }
                }
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
    currentJobSorting: JobSorting,
    onJobSortingClick: () -> Unit,
    onUpdateCurrentJobSorting: (JobTab) -> Unit,
    onFilterClick: () -> Unit
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
            type = type,
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
            currentJobSorting = currentJobSorting,
            onListSortingItemClick = onJobSortingClick,
            onUpdateCurrentJobSorting = onUpdateCurrentJobSorting,
            onFilterIconClick = onFilterClick
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
    type: Type,
) {
    var isArrowExpanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clickableWithNoRipple {
                onClick()
                isArrowExpanded = !isArrowExpanded
            },
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
            if (isArrowExpanded)
                ImageVector.vectorResource(id = R.drawable.job_tab_arrow_up)
            else
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
    currentJobSorting: JobSorting,
    onUpdateCurrentJobSorting: (JobTab) -> Unit,
    onListSortingItemClick: () -> Unit,
    onFilterIconClick: () -> Unit
) {
    JobTab.values().find { it.index == pagerState.currentPage }?.let { jobTab ->
        onUpdateCurrentJobSorting(jobTab)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = FColor.BgGroupedBase)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        JobTab(pagerState, coroutineScope)

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
                        onListSortingItemClick()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(currentJobSorting.currentJobFilterType.titleRes),
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

            IconButton(onClick = { onFilterIconClick() }) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.job_tab_main_filter),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun JobTab(
    pagerState: PagerState,
    coroutineScope: CoroutineScope
) {
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
    );

    companion object {
        fun parsePage(page: String) = JobTab.values().find { it.name == page } ?: JOB_OPENING
    }
}
