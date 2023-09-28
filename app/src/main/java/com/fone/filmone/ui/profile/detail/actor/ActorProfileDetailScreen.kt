package com.fone.filmone.ui.profile.detail.actor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.main.job.common.TagComponent
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.profile.list.model.ProfileListArguments
import com.fone.filmone.ui.recruiting.detail.InfoComponent
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ActorProfileDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ActorProfileDetailViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        },
        topBar = {
            TitleComponent(
                onBackClick = { navController.popBackStack() },
                onMoreImageClick = {}
            )
        },
        bottomBar = {
            ButtonComponent(
                modifier = Modifier,
                onScrapClick = {
                    viewModel.wantProfile()
                },
                onContactClick = viewModel::contact,
                uiState = uiState,
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                HeaderComponent(
                    date = uiState.date,
                    viewCount = uiState.viewCount,
                    profileImageUrl = uiState.profileImageUrl,
                    username = uiState.userNickname,
                    userType = uiState.userType
                )

                ProfileListComponent(
                    title = uiState.articleTitle,
                    imageUrls = uiState.profileImageUrls,
                    userName = uiState.userName
                )

                ActorInfoComponent(
                    name = uiState.userName,
                    gender = uiState.gender,
                    birthday = uiState.birthday,
                    heightWeight = uiState.heightWeight,
                    email = uiState.email,
                    speciality = uiState.specialty,
                    sns = uiState.sns
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                DetailInfoComponent(detail = uiState.detail)

                Divider(thickness = 8.dp, color = FColor.Divider2)

                MainCareerInfoComponent(mainCareer = uiState.careerDetail)

                Divider(thickness = 8.dp, color = FColor.Divider2)

                CategoryComponent(
                    categories = uiState.categories.map { it.name },
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                GuideComponent()
            }
        }
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
        titleText = stringResource(id = R.string.profile_detail_actor_title_text),
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
                imageVector = ImageVector.vectorResource(id = R.drawable.actor_detail_more_vertical),
                contentDescription = null
            )
        }
    )
}

@Composable
private fun CategoryComponent(
    modifier: Modifier = Modifier,
    categories: List<String>,
) {
    Column(
        modifier = modifier
            .padding(vertical = 18.dp, horizontal = 16.dp)
    ) {
        val groupedCategories = categories.chunked(3)

        groupedCategories.forEachIndexed { groupIndex, group ->
            Row(modifier = Modifier.fillMaxWidth()) {
                group.forEachIndexed { index, category ->
                    TagComponent(title = category, enable = false, clickable = false)

                    if (index != group.lastIndex) {
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }

            if (groupIndex != groupedCategories.lastIndex) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun HeaderComponent(
    modifier: Modifier = Modifier,
    date: String,
    viewCount: String,
    profileImageUrl: String,
    username: String,
    userType: String
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = date,
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 13.textDp,
                    lineHeight = 16.textDp,
                    color = FColor.TextSecondary
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.actor_detail_view_count),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = viewCount,
                style = fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 12.textDp,
                    lineHeight = 17.textDp,
                    color = FColor.DisablePlaceholder
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier
                    .size(32.dp)
                    .clip(shape = CircleShape)
                    .background(shape = CircleShape, color = FColor.Divider1),
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = FColor.Gray700,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),
                imageModel = profileImageUrl,
                contentScale = ContentScale.Crop,
                failure = {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clip(shape = CircleShape)
                            .background(shape = CircleShape, color = FColor.Divider1),
                    )
                }
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = username,
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 15.textDp,
                    lineHeight = 20.textDp,
                    color = FColor.TextPrimary
                )
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = userType,
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.Primary
                )
            )
        }
    }
}

@Composable
fun FullScreenPhotoDialog(
    imageUrl: String,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClose() }
        ) {
            GlideImage(
                modifier = Modifier.fillMaxSize()
                    .aspectRatio(103 / 131f)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(shape = RoundedCornerShape(5.dp), color = FColor.Divider1),
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = FColor.Gray700,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),

                imageModel = imageUrl,
                contentScale = ContentScale.Crop,
                failure = {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center),
                        imageVector = ImageVector.vectorResource(id = R.drawable.splash_fone_logo),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
private fun ProfileListComponent(
    modifier: Modifier = Modifier,
    title: String,
    imageUrls: List<String>,
    userName: String,
) {
    var showDialog by remember { mutableStateOf(false) }
    var clickedImageUrl by remember { mutableStateOf("") }

    if (showDialog) {
        FullScreenPhotoDialog(imageUrl = clickedImageUrl) {
            showDialog = false
        }
    }

    @Composable
    fun Photo(
        photoModifier: Modifier = Modifier,
        imageUrl: String,
        onPhotoClick: () -> Unit
    ) {
        GlideImage(
            modifier = photoModifier
                .aspectRatio(103 / 131f)
                .clip(shape = RoundedCornerShape(5.dp))
                .clickable { onPhotoClick() }
                .background(shape = RoundedCornerShape(5.dp), color = FColor.Divider1),
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = FColor.Gray700,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f
            ),

            imageModel = imageUrl,
            contentScale = ContentScale.Crop,
            failure = {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(id = R.drawable.splash_fone_logo),
                    contentDescription = null
                )
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = FColor.Divider2)
            .padding(top = 18.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = title,
            style = LocalTypography.current.h2()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Photo(
                photoModifier = Modifier.weight(1f),
                imageUrl = imageUrls.getOrNull(0) ?: "",
                onPhotoClick = {
                    showDialog = true
                    clickedImageUrl = imageUrls.getOrNull(0) ?: ""
                }
            )

            Spacer(modifier = Modifier.width(17.dp))

            Photo(
                photoModifier = Modifier.weight(1f),
                imageUrl = imageUrls.getOrNull(1) ?: "",
                onPhotoClick = {
                    showDialog = true
                    clickedImageUrl = imageUrls.getOrNull(1) ?: ""
                }
            )

            Spacer(modifier = Modifier.width(17.dp))

            Photo(
                photoModifier = Modifier.weight(1f),
                imageUrl = imageUrls.getOrNull(2) ?: "",
                onPhotoClick = {
                    showDialog = true
                    clickedImageUrl = imageUrls.getOrNull(3) ?: ""
                }
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingleWithNoRipple {
                    FOneNavigator.navigateTo(
                        NavDestinationState(
                            route = FOneDestinations.ProfileList.getRouteWithArg(
                                profileListArgument = ProfileListArguments(
                                    userName,
                                    imageUrls
                                )
                            )
                        )
                    )
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.profile_detail_actor_picture_more_text),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 12.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.TextSecondary
                )
            )

            Image(
                painter = painterResource(id = R.drawable.actor_profile_detail_arrow_right),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ActorInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    gender: Gender,
    birthday: String,
    heightWeight: String,
    email: String,
    speciality: String,
    sns: String
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.profile_detail_actor_info_title),
            style = LocalTypography.current.h3()
        )

        Spacer(modifier = Modifier.height(10.dp))

        InfoComponent(
            title = stringResource(id = R.string.profile_detail_actor_info_name),
            content = name,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.profile_detail_actor_info_gender),
            content = stringResource(id = gender.stringRes),
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.profile_detail_actor_info_birthday),
            content = birthday,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.profile_detail_actor_info_height_weight),
            content = heightWeight,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.profile_detail_actor_info_email),
            content = email,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.profile_detail_actor_info_ability),
            content = speciality,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.profile_detail_actor_info_sns),
            content = sns,
        )
    }
}

@Composable
private fun DetailInfoComponent(
    modifier: Modifier = Modifier,
    detail: String
) {
    Column(modifier = modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.profile_detail_actor_detail_title),
            style = LocalTypography.current.h3(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = detail,
            style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.textDp,
                lineHeight = 19.textDp,
                color = FColor.TextPrimary
            )
        )
    }
}

@Composable
private fun MainCareerInfoComponent(
    modifier: Modifier = Modifier,
    mainCareer: String
) {
    Column(modifier = modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.profile_detail_actor_main_career_title),
            style = LocalTypography.current.h3(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = mainCareer,
            style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.textDp,
                lineHeight = 19.textDp,
                color = FColor.TextPrimary
            )
        )
    }
}

@Composable
private fun GuideComponent(
    modifier: Modifier = Modifier
) {
    val guideTextStyle = fTextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 12.textDp,
        lineHeight = 17.textDp,
        color = FColor.DisablePlaceholder
    )

    Column(
        modifier = modifier
            .background(color = FColor.Divider2)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 40.dp)
    ) {
        Text(
            text = stringResource(id = R.string.profile_detail_actor_guide_1),
            style = guideTextStyle
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.profile_detail_actor_guide_2),
            style = guideTextStyle
        )
    }
}

@Composable
private fun ButtonComponent(
    modifier: Modifier = Modifier,
    onScrapClick: () -> Unit,
    onContactClick: () -> Unit,
    uiState: ActorProfileDetailUiState
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(shape = RoundedCornerShape(5.dp), color = FColor.BgGroupedBase)
                .weight(140f)
                .clickableSingle { onScrapClick() }
                .padding(vertical = 11.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                if (uiState.isWant) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.favorite_selected),
                        contentDescription = null
                    )
                } else {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.favorite_unselected),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = stringResource(id = R.string.profile_detail_actor_favorite_button_title),
                    style = fTextStyle(
                        fontWeight = FontWeight.W500,
                        fontSize = 16.textDp,
                        lineHeight = 18.textDp,
                        color = FColor.TextSecondary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(shape = RoundedCornerShape(5.dp), color = FColor.Primary)
                .weight(195f)
                .clickableSingle { onContactClick() }
                .padding(vertical = 11.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(id = R.string.profile_detail_actor_contact_button_title),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 16.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.White
                )
            )
        }
    }
}
