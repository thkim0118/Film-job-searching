package com.fone.filmone.ui.recruiting.detail.staff

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import com.fone.filmone.ui.recruiting.detail.InfoComponent
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun StaffRecruitingDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: StaffRecruitingDetailViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TitleComponent(
                onBackClick = { navController.popBackStack() },
                onMoreImageClick = {}
            )

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

                DetailTitleComponent(
                    categories = uiState.categories.map { it.name },
                    articleTitle = uiState.articleTitle
                )

                RecruitmentConditionComponent(
                    deadline = uiState.deadline,
                    dday = uiState.dday,
                    role = uiState.role,
                    number = uiState.numberOfRecruits,
                    gender = uiState.gender,
                    ageRange = uiState.ageRange,
                    career = uiState.career
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                WorkInfoComponent(
                    production = uiState.production,
                    workTitle = uiState.workTitle,
                    director = uiState.director,
                    genre = uiState.genre,
                    logLine = uiState.logLine
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                WorkingConditionsComponent(
                    location = uiState.location,
                    period = uiState.period,
                    pay = uiState.pay
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                DetailInfoComponent(detail = uiState.detailInfo)

                Divider(thickness = 8.dp, color = FColor.Divider2)

                ManagerInfoComponent(
                    manager = uiState.manager,
                    email = uiState.email
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                GuideComponent()
            }

            ButtonComponent(
                onScrapClick = {},
                onContactClick = {}
            )
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
        titleText = stringResource(id = R.string.actor_detail_title_text),
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
private fun DetailTitleComponent(
    modifier: Modifier = Modifier,
    categories: List<String>,
    articleTitle: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = FColor.Divider2)
            .padding(vertical = 18.dp, horizontal = 16.dp)
    ) {
        Row(modifier = Modifier) {
            categories.forEachIndexed { index, category ->
                TagComponent(title = category, enable = true, clickable = false)

                if (categories.lastIndex != index) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = articleTitle,
            style = LocalTypography.current.h2(),
            color = FColor.TextPrimary
        )
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
            if (profileImageUrl.isEmpty()) {
                Image(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(32.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
                    contentDescription = null
                )
            } else {
                GlideImage(
                    modifier = modifier
                        .size(32.dp)
                        .clip(shape = CircleShape),
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
            }

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
private fun RecruitmentConditionComponent(
    modifier: Modifier = Modifier,
    deadline: String,
    dday: String,
    role: String,
    number: String,
    gender: Gender,
    ageRange: String,
    career: String
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.actor_detail_recruitment_condition_title),
            style = LocalTypography.current.h3()
        )

        Spacer(modifier = Modifier.height(10.dp))

        DeadlineComponent(
            deadline = deadline,
            dday = dday
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_recruitment_role_title),
            content = role,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_recruitment_number_of_recruits_title),
            content = number,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.staff_detail_recruitment_gender_title),
            content = gender.name,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_recruitment_age_title),
            content = ageRange,
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_recruitment_career_title),
            content = career,
        )
    }
}

@Composable
private fun WorkInfoComponent(
    modifier: Modifier = Modifier,
    production: String,
    workTitle: String,
    director: String,
    genre: String,
    logLine: String
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.actor_detail_work_info_title),
            style = LocalTypography.current.h3(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_work_production_title),
            content = production
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_work_director_title),
            content = director
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_work_title),
            content = workTitle
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_work_genre_title),
            content = genre
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_work_log_line_title),
            content = logLine
        )
    }
}

@Composable
private fun WorkingConditionsComponent(
    modifier: Modifier = Modifier,
    location: String,
    period: String,
    pay: String
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.actor_detail_working_conditions_title),
            style = LocalTypography.current.h3(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_location_title),
            content = location
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_period_title),
            content = period
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_pay_title),
            content = pay
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
            text = stringResource(id = R.string.actor_detail_detail_info_title),
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
private fun ManagerInfoComponent(
    modifier: Modifier = Modifier,
    manager: String,
    email: String
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.actor_detail_manager_info),
            style = LocalTypography.current.h3(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_manager_name_title),
            content = manager
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoComponent(
            title = stringResource(id = R.string.actor_detail_email_title),
            content = email
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
            text = stringResource(id = R.string.actor_detail_guide_contents_1),
            style = guideTextStyle
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.actor_detail_guide_contents_2),
            style = guideTextStyle
        )
    }
}

@Composable
private fun ButtonComponent(
    modifier: Modifier = Modifier,
    onScrapClick: () -> Unit,
    onContactClick: () -> Unit
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
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.actor_detaile_scrap_disable),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = stringResource(id = R.string.actor_detail_scrap_button_title),
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
                .background(shape = RoundedCornerShape(5.dp), color = FColor.BgGroupedBase)
                .weight(195f)
                .clickableSingle { onContactClick() }
        ) {
            Text(
                text = stringResource(id = R.string.actor_detail_contact_button_title),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 16.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.Primary
                )
            )
        }
    }
}

@Composable
private fun DeadlineComponent(
    modifier: Modifier = Modifier,
    deadline: String,
    dday: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier
                .weight(68f),
            text = stringResource(id = R.string.actor_detail_recruitment_deadline_title),
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            ),
        )

        Row(
            modifier = Modifier
                .weight(268f)
        ) {
            Text(
                text = deadline,
                style = fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.textDp,
                    lineHeight = 19.textDp,
                    color = FColor.TextPrimary
                )
            )

            Spacer(modifier = Modifier.width(21.dp))

            TagComponent(title = dday, enable = true, clickable = false)
        }
    }
}
