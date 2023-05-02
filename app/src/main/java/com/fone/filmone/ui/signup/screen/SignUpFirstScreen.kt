package com.fone.filmone.ui.signup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.response.common.user.Category
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.signup.SignUpFirstUiState
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
    signUpVo: SignUpVo,
    viewModel: SignUpFirstViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

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
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
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
                    style = LocalTypography.current.h1(),
                    color = FColor.TextPrimary
                )

                Spacer(modifier = Modifier.height(32.dp))

                ChoiceTitle(
                    title = stringResource(id = R.string.sign_up_first_choice_job),
                    subtitle = stringResource(id = R.string.sign_up_first_choice_job_subtitle)
                )

                Spacer(modifier = Modifier.height(8.dp))

                JobTags(
                    currentJob = uiState.job,
                    onUpdateJob = viewModel::updateJobTag
                )

                Spacer(modifier = Modifier.height(40.dp))

                ChoiceTitle(
                    title = stringResource(id = R.string.sign_up_first_choice_favorite),
                    subtitle = stringResource(id = R.string.sign_up_first_choice_favorite_subtitle)
                )

                Spacer(modifier = Modifier.height(8.dp))

                InterestsTags(
                    currentInterests = uiState.interests,
                    onUpdateInterests = viewModel::updateInterest
                )

                Spacer(modifier = Modifier.height(203.dp))
            }

            NextButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                signUpVo = signUpVo,
                uiState = uiState
            )

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

@Composable
private fun ColumnScope.NextButton(
    modifier: Modifier = Modifier,
    uiState: SignUpFirstUiState,
    signUpVo: SignUpVo
) {
    val enable = uiState.job != null && uiState.interests.isNotEmpty()

    Spacer(modifier = Modifier.weight(1f))

    FButton(
        modifier = modifier,
        title = stringResource(id = R.string.sign_up_next_title),
        enable = enable,
        onClick = {
            if (enable) {
                FOneNavigator.navigateTo(
                    NavDestinationState(
                        route = FOneDestinations.SignUpSecond.getRouteWithArg(
                            signUpVo.copy(
                                job = uiState.job?.name ?: return@FButton,
                                interests = uiState.interests.map { it.name }
                            )
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
                fontSize = 15.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextPrimary
            )
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = subtitle,
            style = LocalTypography.current.label(),
            color = FColor.DisablePlaceholder
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JobTags(
    modifier: Modifier = Modifier,
    currentJob: Job?,
    onUpdateJob: (Job) -> Unit
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        Job.values().forEach { job ->
            JobTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                job = job,
                isSelected = currentJob == job,
                onClick = onUpdateJob
            )
        }
    }
}

@Composable
private fun JobTag(
    modifier: Modifier = Modifier,
    job: Job,
    isSelected: Boolean,
    onClick: (Job) -> Unit
) {
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
            .clickable { onClick(job) },
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = job.name,
            style = if (isSelected) {
                fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.textDp,
                    lineHeight = 16.8.textDp,
                    color = FColor.Primary
                )
            } else {
                fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.textDp,
                    lineHeight = 16.8.textDp,
                    color = FColor.DisablePlaceholder
                )
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InterestsTags(
    modifier: Modifier = Modifier,
    currentInterests: List<Category>,
    onUpdateInterests: (Category, Boolean) -> Unit
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        Category.values().forEach { interests ->
            InterestsTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                category = interests,
                isSelected = currentInterests.find { it == interests } != null,
                onClick = onUpdateInterests
            )
        }
    }
}

@Composable
private fun InterestsTag(
    modifier: Modifier = Modifier,
    category: Category,
    isSelected: Boolean,
    onClick: (Category, Boolean) -> Unit
) {
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
            .clickable { onClick(category, isSelected.not()) },
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = stringResource(id = category.stringRes),
            style = if (isSelected) {
                fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.textDp,
                    lineHeight = 16.8.textDp,
                    color = FColor.Primary
                )
            } else {
                fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.textDp,
                    lineHeight = 16.8.textDp,
                    color = FColor.DisablePlaceholder
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JobTagsPreview() {
    JobTags(
        currentJob = Job.HUNTER,
        onUpdateJob = {}
    )
}


@Preview(showBackground = true)
@Composable
private fun FavoriteTagsPreview() {
    InterestsTags(
        currentInterests = Category.values().toList(),
        onUpdateInterests = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun SignUpFirstScreenPreview() {
    FilmOneTheme {
        SignUpFirstScreen(signUpVo = SignUpVo())
    }
}