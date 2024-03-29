package com.fone.filmone.ui.signup.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.tag.categories.CategoryTags
import com.fone.filmone.ui.common.tag.job.JobTags
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

    LaunchedEffect(signUpVo) {
        viewModel.updateSavedSignupVo(signUpVo)
    }

    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .fillMaxSize()
    ) {
        FTitleBar(
            titleType = TitleType.Back,
            titleText = stringResource(id = R.string.sign_up_title_text),
            onBackClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        "savedSignupVo",
                        SignUpVo.toJson(
                            signUpVo.copy(
                                job = uiState.job?.name ?: "",
                                interests = uiState.interests.map { it.name }
                            )
                        )
                    )
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

                CategoryTags(
                    currentCategories = uiState.interests,
                    onUpdateCategories = viewModel::updateInterest
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
            style = LocalTypography.current.subtitle1()
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = subtitle,
            style = LocalTypography.current.label(),
            color = FColor.DisablePlaceholder
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
    CategoryTags(
        currentCategories = Category.values().toList(),
        onUpdateCategories = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun SignUpFirstScreenPreview() {
    FilmOneTheme {
        SignUpFirstScreen(signUpVo = SignUpVo())
    }
}
