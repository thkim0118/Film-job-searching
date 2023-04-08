package com.fone.filmone.ui.signup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.domain.model.signup.Interests
import com.fone.filmone.domain.model.signup.Job
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.signup.SignUpFirstViewModel
import com.fone.filmone.ui.signup.components.IndicatorType
import com.fone.filmone.ui.signup.components.SignUpIndicator
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun SignUpFirstScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    signUpVo: SignUpVo
) {
    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .fillMaxSize()
    ) {
        FTitleBar(
            titleType = TitleType.Back,
            titleText = stringResource(id = R.string.sign_up_title_text),
            onBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            SignUpIndicator(indicatorType = IndicatorType.First)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(id = R.string.sign_up_first_title),
                style = LocalTypography.current.h1,
                color = FColor.TextPrimary
            )

            Spacer(modifier = Modifier.height(32.dp))

            ChoiceTitle(
                title = stringResource(id = R.string.sign_up_first_choice_job),
                subtitle = stringResource(id = R.string.sign_up_first_choice_job_subtitle)
            )

            Spacer(modifier = Modifier.height(8.dp))

            JobTags()

            Spacer(modifier = Modifier.height(40.dp))

            ChoiceTitle(
                title = stringResource(id = R.string.sign_up_first_choice_favorite),
                subtitle = stringResource(id = R.string.sign_up_first_choice_favorite_subtitle)
            )

            Spacer(modifier = Modifier.height(8.dp))

            InterestsTags()

            Spacer(modifier = Modifier.weight(1f))

            NextButton(signUpVo = signUpVo)

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

@Composable
private fun NextButton(
    viewModel: SignUpFirstViewModel = hiltViewModel(),
    signUpVo: SignUpVo
) {
    val uiState by viewModel.uiState.collectAsState()
    val enable = uiState.job != null && uiState.interests.isNotEmpty()

    FButton(
        title = stringResource(id = R.string.sign_up_next_title),
        enable = enable,
        onClick = {
            if (enable) {
                FOneNavigator.navigateTo(
                    FOneDestinations.SignUpSecond.getRouteWithArg(
                        signUpVo.copy(
                            job = uiState.job?.name ?: return@FButton,
                            interests = uiState.interests.map { it.name }
                        )
                    )
                )
            }
        }
    )
}

@Composable
private fun ChoiceTitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                color = FColor.TextPrimary
            )
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = subtitle,
            style = LocalTypography.current.label,
            color = FColor.DisablePlaceholder
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JobTags(
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        Job.values().forEach { job ->
            JobTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                job = job,
            )
        }
    }
}

@Composable
private fun JobTag(
    modifier: Modifier = Modifier,
    job: Job,
    viewModel: SignUpFirstViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isSelected = uiState.job == job

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(90.dp))
            .background(
                color = if (isSelected) {
                    FColor.Red50
                } else {
                    FColor.BgGroupedBase
                },
                shape = RoundedCornerShape(90.dp)
            )
            .clickable { viewModel.updateJobTag(job) },
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = job.name,
            style = if (isSelected) {
                fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    color = FColor.Primary
                )
            } else {
                fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    color = FColor.DisablePlaceholder
                )
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InterestsTags(
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        Interests.values().forEach { interests ->
            InterestsTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                interests = interests,
            )
        }
    }
}

@Composable
private fun InterestsTag(
    modifier: Modifier = Modifier,
    interests: Interests,
    viewModel: SignUpFirstViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isSelected = uiState.interests.find { it == interests } != null

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(90.dp))
            .background(
                color = if (isSelected) {
                    FColor.Red50
                } else {
                    FColor.BgGroupedBase
                },
                shape = RoundedCornerShape(90.dp)
            )
            .clickable { viewModel.updateInterest(interests, isSelected.not()) },
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = interests.title,
            style = if (isSelected) {
                fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    color = FColor.Primary
                )
            } else {
                fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    color = FColor.DisablePlaceholder
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JobTagsPreview() {
    JobTags()
}


@Preview(showBackground = true)
@Composable
private fun FavoriteTagsPreview() {
    InterestsTags()
}

@Preview(showBackground = true)
@Composable
private fun SignUpFirstScreenPreview() {
    FilmOneTheme {
        SignUpFirstScreen(signUpVo = SignUpVo())
    }
}