package com.fone.filmone.ui.main.home

import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import com.fone.filmone.ui.util.AppLifecycleObserver
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onJobButtonClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = uiState.homeUiEvent.currentBannerPage,
        pageCount = { Int.MAX_VALUE }
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    DisposableEffect(lifecycleOwner) {
        val observer = AppLifecycleObserver(
            onEvent = { event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> viewModel.startTimer()
                    Lifecycle.Event.ON_PAUSE -> viewModel.stopTimer()
                    else -> Unit
                }
            },
        )
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.startTimer()
    }

    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding(),
    ) {
        HomeTitleComponent()

        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
        ) {
            BannerPagerComponent(
                pagerState = pagerState,
                currentPage = uiState.homeUiEvent.currentBannerPage,
                bannerContents = HomeViewModel.bannerContents,
                isBannerTouched = uiState.homeUiEvent.isBannerTouched,
                onBannerTouched = {
                    viewModel.onUpdateBannerTouched()
                },
                onUpdatePage = { pageCount ->
                    viewModel.onUpdatePageCount(pageCount)
                },
                onBannerComposableDispose = {
                    viewModel.stopTimer()
                },
            )

            Spacer(modifier = Modifier.height(32.dp))

            RecommendedCompetitionComponent(
                recommendedContents = uiState.recommendedJobOpening.recommendedContents,
                onItemClick = { competitionId, type ->
                    FOneNavigator.navigateTo(
                        NavDestinationState(
                            route = when (type) {
                                Type.ACTOR -> {
                                    FOneDestinations.ActorRecruitingDetail.getRouteWithArg(
                                        competitionId = competitionId,
                                    )
                                }

                                Type.STAFF -> {
                                    FOneDestinations.StaffRecruitingDetail.getRouteWithArg(
                                        competitionId = competitionId,
                                    )
                                }
                            },
                        ),
                    )
                },
                onJobButtonClick = onJobButtonClick,
            )

            Spacer(modifier = Modifier.height(40.dp))

            PopularityCompetitionComponent(
                popularityContents = uiState.popularityCompetition.popularityContents,
                onItemClick = { competitionId, type ->
                    FOneNavigator.navigateTo(
                        NavDestinationState(
                            route = when (type) {
                                Type.ACTOR -> {
                                    FOneDestinations.ActorRecruitingDetail.getRouteWithArg(
                                        competitionId = competitionId,
                                    )
                                }

                                Type.STAFF -> {
                                    FOneDestinations.StaffRecruitingDetail.getRouteWithArg(
                                        competitionId = competitionId,
                                    )
                                }
                            },
                        ),
                    )
                },
            )

            ActorProfileComponent(
                homeActorProfileContents = uiState.homeActorProfile.homeActorProfileContents,
                onItemClick = { profileId ->
                    FOneNavigator.navigateTo(
                        NavDestinationState(
                            route = FOneDestinations.ActorProfileDetail.getRouteWithArg(profileId = profileId),
                        ),
                    )
                },
                onJobButtonClick = onJobButtonClick,
            )
        }
    }
}

@Composable
private fun HomeTitleComponent(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(min = 50.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            stringResource(id = R.string.home_title_text),
            style = LocalTypography.current.h2(),
            color = FColor.TextPrimary,
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.main_notification),
                contentDescription = null,
                tint = FColor.DisablePlaceholder,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun BannerPagerComponent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    currentPage: Int,
    @DrawableRes bannerContents: List<Int>,
    isBannerTouched: Boolean,
    onBannerTouched: () -> Unit,
    onUpdatePage: (pageCount: Int) -> Unit,
    onBannerComposableDispose: () -> Unit,
) {
    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }

    if (isBannerTouched && pagerState.currentPage != currentPage) {
        onUpdatePage(pagerState.currentPage)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            onBannerComposableDispose()
        }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            modifier = Modifier
                .pointerInteropFilter { motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            onBannerTouched()
                        }
                    }

                    false
                },
            state = pagerState,
        ) { pageCount ->
            Image(
                painter = painterResource(id = bannerContents[pageCount % bannerContents.size]),
                contentDescription = null,
            )
        }

        Box(
            modifier = Modifier
                .padding(end = 9.dp, bottom = 18.dp)
                .clip(shape = RoundedCornerShape(15.5.dp))
                .background(shape = RoundedCornerShape(15.5.dp), color = FColor.DimColorBasic)
                .padding(horizontal = 14.dp, vertical = 3.dp)
                .align(Alignment.BottomEnd),
        ) {
            Text(
                modifier = Modifier,
                text = "${(pagerState.currentPage % bannerContents.size) + 1}/${bannerContents.size}",
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.textDp,
                    lineHeight = 16.8.textDp,
                    color = FColor.BgBase,
                ),
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecommendedCompetitionComponent(
    modifier: Modifier = Modifier,
    recommendedContents: List<RecommendedContent>,
    onItemClick: (competitionId: Int, type: Type) -> Unit,
    onJobButtonClick: () -> Unit,
) {
    val backgroundContents = listOf(
        R.drawable.home_card1 to FColor.Divider2,
        R.drawable.home_card2 to FColor.TextSecondary,
        R.drawable.home_card3 to FColor.Divider2,
        R.drawable.home_card4 to FColor.Divider2,
        R.drawable.home_card5 to FColor.TextSecondary,
        R.drawable.home_card6 to FColor.TextSecondary,
    )

    @Composable
    fun Tag(
        modifier: Modifier = Modifier,
        title: String,
    ) {
        Box(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(3.dp),
                    color = Color(alpha = 0f, red = 1f, green = 1f, blue = 1f),
                )
                .clip(shape = RoundedCornerShape(3.dp))
                .border(width = 1.dp, color = Color(alpha = 0.1f, red = 1f, green = 1f, blue = 1f))
                .padding(horizontal = 8.dp, vertical = 2.dp),
        ) {
            Text(
                text = title,
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 12.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.Divider2,
                ),
            )
        }
    }

    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.home_recommended_title),
                style = LocalTypography.current.h4(),
            )

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.home_recommended_right_arrow),
                contentDescription = null,
                modifier = Modifier.clickableWithNoRipple {
                    onJobButtonClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (recommendedContents.isEmpty()) {
            ServicePreparingGuideComponent()
        } else {
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                itemsIndexed(recommendedContents) { index, content ->
                    Box(
                        modifier = Modifier
                            .clickableSingle { onItemClick(content.id, content.type) },
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(backgroundContents[index % recommendedContents.size].first),
                            contentDescription = null,
                        )

                        Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 14.dp)) {
                            Text(
                                text = content.title,
                                style = fTextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 14.textDp,
                                    lineHeight = 18.textDp,
                                    color = FColor.White,
                                ),
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(max = 130.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = content.subtitle,
                                style = fTextStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 12.textDp,
                                    lineHeight = 17.textDp,
                                    color = backgroundContents[index % recommendedContents.size].second,
                                ),
                            )

                            Text(
                                text = content.dday,
                                style = fTextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 13.textDp,
                                    lineHeight = 16.textDp,
                                    color = backgroundContents[index % recommendedContents.size].second,
                                ),
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(max = 130.dp)
                            ) {
                                content.tagStringResources.forEach { tagItem ->
                                    Tag(
                                        modifier = Modifier.padding(end = 6.dp, bottom = 6.dp),
                                        title = stringResource(id = tagItem),
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PopularityCompetitionComponent(
    modifier: Modifier = Modifier,
    popularityContents: List<PopularityContent>,
    onItemClick: (competitionId: Int, type: Type) -> Unit,
) {
    @Composable
    fun Competition(
        modifier: Modifier = Modifier,
        competitionId: Int,
        type: Type,
        imageUrl: String,
        index: Int,
    ) {
        val cornerModifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(shape = RoundedCornerShape(10.dp), color = FColor.Transparent)

        Box(
            modifier = cornerModifier
                .clickableSingle { onItemClick(competitionId, type) },
        ) {
            GlideImage(
                modifier = modifier
                    .size(width = 148.dp, height = 165.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = FColor.Gray700,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f,
                ),
                imageModel = imageUrl,
                contentScale = ContentScale.Crop,
                failure = {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center),
                        imageVector = ImageVector.vectorResource(id = R.drawable.splash_fone_logo),
                        contentDescription = null,
                    )
                },
            )

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(topStart = 10.dp))
                    .background(
                        shape = RoundedCornerShape(topStart = 10.dp),
                        color = FColor.Primary,
                    )
                    .padding(9.dp)
                    .align(Alignment.TopStart),
            ) {
                Text(text = "$index")
            }
        }
    }

    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.home_popularity_competition_title),
                style = LocalTypography.current.h4(),
            )

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.home_recommended_right_arrow),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (popularityContents.isEmpty()) {
            ServicePreparingGuideComponent()
        } else {
            LazyRow {
                itemsIndexed(popularityContents) { index, item ->
                    Competition(
                        competitionId = item.id,
                        type = item.type,
                        imageUrl = item.imageUrl,
                        index = index,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun ActorProfileComponent(
    modifier: Modifier = Modifier,
    homeActorProfileContents: List<HomeActorProfileContent>,
    onItemClick: (profileId: Int) -> Unit,
    onJobButtonClick: () -> Unit,
) {
    @Composable
    fun ActorProfile(
        modifier: Modifier = Modifier,
        homeActorProfileContent: HomeActorProfileContent,
        onItemClick: (profileId: Int) -> Unit,
    ) {
        val shape = RoundedCornerShape(10.dp)
        val imageModifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .background(shape = RoundedCornerShape(5.dp), color = FColor.Divider1)

        Column(
            modifier = modifier
                .width(241.dp)
                .clip(shape = shape)
                .background(shape = shape, color = FColor.White)
                .border(width = 1.dp, color = FColor.BgGroupedBase)
                .clickableSingle { onItemClick(homeActorProfileContent.id) }
                .padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = homeActorProfileContent.hookingComment,
                style = LocalTypography.current.subtitle1(),
                color = FColor.TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier,
            ) {
                Box(modifier = imageModifier) {
                    GlideImage(
                        modifier = modifier
                            .size(width = 60.dp, height = 60.dp)
                            .clip(shape = RoundedCornerShape(5.dp)),
                        shimmerParams = ShimmerParams(
                            baseColor = MaterialTheme.colors.background,
                            highlightColor = FColor.Gray700,
                            durationMillis = 350,
                            dropOff = 0.65f,
                            tilt = 20f,
                        ),

                        imageModel = homeActorProfileContent.imageUrl,
                        contentScale = ContentScale.Crop,
                        failure = {
                            Image(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                imageVector = ImageVector.vectorResource(id = R.drawable.splash_fone_logo),
                                contentDescription = null,
                            )
                        },
                    )
                }

                Spacer(modifier = Modifier.width(11.dp))

                Column(modifier = Modifier) {
                    Text(
                        text = homeActorProfileContent.name,
                        style = LocalTypography.current.h5(),
                        color = FColor.TextPrimary,
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = homeActorProfileContent.ageContent,
                        style = LocalTypography.current.b2(),
                        color = FColor.TextSecondary,
                    )

                    Text(
                        text = stringResource(homeActorProfileContent.gender.stringRes),
                        style = LocalTypography.current.b2(),
                        color = FColor.TextSecondary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))
        }
    }

    Column(modifier = modifier.background(color = FColor.Divider2)) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.home_actor_profile_title),
                style = LocalTypography.current.h4(),
            )

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.home_recommended_right_arrow),
                contentDescription = null,
                modifier = Modifier.clickableWithNoRipple {
                    onJobButtonClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (homeActorProfileContents.isEmpty()) {
            ServicePreparingGuideComponent()
        } else {
            LazyRow(
                contentPadding = PaddingValues(16.dp),
            ) {
                itemsIndexed(homeActorProfileContents) { index, homeActorProfile ->
                    ActorProfile(
                        homeActorProfileContent = homeActorProfile,
                        onItemClick = onItemClick,
                    )

                    if (homeActorProfileContents.lastIndex != index) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun ServicePreparingGuideComponent(
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(10.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(shape = shape)
            .background(shape = shape, color = FColor.White)
            .border(width = 1.dp, color = FColor.BgGroupedBase, shape = shape),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.service_image),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(id = R.string.home_service_guide_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.home_service_guide_subtitle),
            style = LocalTypography.current.label(),
            color = FColor.TextSecondary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(37.dp))
    }
}
