package com.fone.filmone.ui.recruiting.common.actor

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.Scaffold
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.FBorderButton
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.tag.career.CareerTags
import com.fone.filmone.ui.common.tag.categories.CategoryTags
import com.fone.filmone.ui.main.job.common.LeftTitleTextField
import com.fone.filmone.ui.main.job.common.TextLimitComponent
import com.fone.filmone.ui.main.job.common.TextWithRequired
import com.fone.filmone.ui.main.job.common.TextWithRequiredTag
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingFocusEvent
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingUiEvent
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingUiModel
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import java.util.regex.Pattern

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ActorRecruitingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: ActorRecruitingUiModel,
    baseViewModel: BaseViewModel,
    onRegisterClick: () -> Unit,
    onUpdateTitleText: (String) -> Unit,
    onUpdateCategories: (Category, Boolean) -> Unit,
    onUpdateDeadlineDate: (String) -> Unit,
    onUpdateRecruitmentActor: (String) -> Unit,
    onUpdateRecruitmentNumber: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateDeadlineTag: (Boolean) -> Unit,
    onUpdateGenderTag: () -> Unit,
    onUpdateAgeReset: () -> Unit,
    onUpdateAgeRange: (ClosedFloatingPointRange<Float>) -> Unit,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerTag: (Boolean) -> Unit,
    onUpdateProduction: (String) -> Unit,
    onUpdateWorkTitle: (String) -> Unit,
    onUpdateDirectorName: (String) -> Unit,
    onUpdateGenre: (String) -> Unit,
    onUpdateLogLine: (String) -> Unit,
    onUpdateLogLineTag: (Boolean) -> Unit,
    onUpdateLocationTag: (Boolean) -> Unit,
    onUpdatePeriodTag: (Boolean) -> Unit,
    onUpdatePayTag: (Boolean) -> Unit,
    onUpdateLocation: (String) -> Unit,
    onUpdatePeriod: (String) -> Unit,
    onUpdatePay: (String) -> Unit,
    onUpdateDetailInfo: (String) -> Unit,
    onUpdateManager: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusEvent = uiState.focusEvent
    val keyboardController = LocalSoftwareKeyboardController.current

    ListenUiEvent(
        uiEvent = uiState.actorRecruitingUiEvent,
        navController = navController,
    )

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = baseViewModel, hostState = it)
        },
    ) {
        Column(
            modifier = Modifier
                .clickableWithNoRipple {
                    keyboardController?.hide()
                }
                .padding(it),
        ) {
            TitleComponent(
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterClick = onRegisterClick,
            )

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState),
            ) {
                Step1Component(
                    titleText = uiState.actorRecruitingStep1UiModel.titleText,
                    titleTextLimit = uiState.actorRecruitingStep1UiModel.titleTextLimit,
                    currentCategories = uiState.actorRecruitingStep1UiModel.categories,
                    deadlineDate = uiState.actorRecruitingStep1UiModel.deadlineDate,
                    deadlineDateTagEnable = uiState.actorRecruitingStep1UiModel.deadlineTagEnable,
                    recruitmentActor = uiState.actorRecruitingStep1UiModel.recruitmentActor,
                    recruitmentNumber = uiState.actorRecruitingStep1UiModel.recruitmentNumber,
                    currentGender = uiState.actorRecruitingStep1UiModel.recruitmentGender,
                    genderTagEnable = uiState.actorRecruitingStep1UiModel.genderTagEnable,
                    defaultAgeRange = uiState.actorRecruitingStep1UiModel.defaultAgeRange,
                    currentAgeRange = uiState.actorRecruitingStep1UiModel.ageRange,
                    currentCareer = uiState.actorRecruitingStep1UiModel.career,
                    ageTagEnable = uiState.actorRecruitingStep1UiModel.ageTagEnable,
                    careerTagEnable = uiState.actorRecruitingStep1UiModel.careerTagEnable,
                    recruitingRoleTextLimit = uiState.actorRecruitingStep1UiModel.recruitingRoleTextLimit,
                    recruitingMemberTextLimit = uiState.actorRecruitingStep1UiModel.recruitingMemberTextLimit,
                    focusEvent = focusEvent,
                    onUpdateTitleText = onUpdateTitleText,
                    onUpdateCategories = onUpdateCategories,
                    onUpdateDeadlineDate = onUpdateDeadlineDate,
                    onUpdateRecruitmentActor = onUpdateRecruitmentActor,
                    onUpdateRecruitmentNumber = onUpdateRecruitmentNumber,
                    onUpdateGender = onUpdateGender,
                    onUpdateAgeReset = onUpdateAgeReset,
                    onUpdateAgeRange = onUpdateAgeRange,
                    onUpdateCareer = onUpdateCareer,
                    onUpdateDeadlineTag = onUpdateDeadlineTag,
                    onUpdateGenderTag = onUpdateGenderTag,
                    onUpdateCareerTag = onUpdateCareerTag,
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                Step2Component(
                    production = uiState.actorRecruitingStep2UiModel.production,
                    workTitle = uiState.actorRecruitingStep2UiModel.workTitle,
                    directorName = uiState.actorRecruitingStep2UiModel.directorName,
                    genre = uiState.actorRecruitingStep2UiModel.genre,
                    logLine = uiState.actorRecruitingStep2UiModel.logLine,
                    logLineTextLimit = uiState.actorRecruitingStep2UiModel.logLineTextLimit,
                    logLineTagEnable = uiState.actorRecruitingStep2UiModel.isLogLineTagEnable,
                    focusEvent = focusEvent,
                    onUpdateProduction = onUpdateProduction,
                    onUpdateWorkTitle = onUpdateWorkTitle,
                    onUpdateDirectorName = onUpdateDirectorName,
                    onUpdateGenre = onUpdateGenre,
                    onUpdateLogLine = onUpdateLogLine,
                    onUpdateLogLineTag = onUpdateLogLineTag,
                    productionTextLimit = uiState.actorRecruitingStep2UiModel.productionTextLimit,
                    workTitleTextLimit = uiState.actorRecruitingStep2UiModel.workTitleTextLimit,
                    directorNameTextLimit = uiState.actorRecruitingStep2UiModel.directorNameTextLimit,
                    genreTextLimit = uiState.actorRecruitingStep2UiModel.genreTextLimit,
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                Step3Component(
                    location = uiState.actorRecruitingStep3UiModel.location,
                    period = uiState.actorRecruitingStep3UiModel.period,
                    pay = uiState.actorRecruitingStep3UiModel.pay,
                    locationTagEnable = uiState.actorRecruitingStep3UiModel.locationTagEnable,
                    periodTagEnable = uiState.actorRecruitingStep3UiModel.periodTagEnable,
                    payTagEnable = uiState.actorRecruitingStep3UiModel.payTagEnable,
                    onUpdateLocationTag = onUpdateLocationTag,
                    onUpdatePeriodTag = onUpdatePeriodTag,
                    onUpdatePayTag = onUpdatePayTag,
                    onUpdateLocation = onUpdateLocation,
                    onUpdatePeriod = onUpdatePeriod,
                    onUpdatePay = onUpdatePay,
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                Step4Component(
                    detailInfo = uiState.actorRecruitingStep4UiModel.detailInfo,
                    detailInfoTextLimit = uiState.actorRecruitingStep4UiModel.detailInfoTextLimit,
                    focusEvent = focusEvent,
                    onUpdateDetailInfo = onUpdateDetailInfo,
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                Step5Component(
                    manager = uiState.actorRecruitingStep5UiModel.manager,
                    email = uiState.actorRecruitingStep5UiModel.email,
                    managerTextLimit = uiState.actorRecruitingStep5UiModel.managerTextLimit,
                    focusEvent = focusEvent,
                    onUpdateManager = onUpdateManager,
                    onUpdateEmail = onUpdateEmail,
                    onRegisterClick = onRegisterClick,
                )
            }
        }
    }
}

@Composable
private fun ListenUiEvent(
    uiEvent: ActorRecruitingUiEvent,
    navController: NavHostController,
) {
    LaunchedEffect(key1 = uiEvent) {
        when (uiEvent) {
            ActorRecruitingUiEvent.Clear -> Unit
            ActorRecruitingUiEvent.RegisterComplete -> {
                navController.popBackStack()
            }
        }
    }
}

@Composable
private fun TitleComponent(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    FTitleBar(
        titleText = stringResource(id = R.string.recruiting_register_actor_title_text),
        leading = {
            Image(
                modifier = Modifier
                    .clickableSingleWithNoRipple { onBackClick() },
                imageVector = ImageVector.vectorResource(id = R.drawable.title_bar_back),
                contentDescription = null,
            )
        },
        action = {
            Text(
                modifier = Modifier
                    .clickableSingle { onRegisterClick() },
                text = stringResource(id = R.string.recruiting_register_actor_title_right_button),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 15.textDp,
                    lineHeight = 20.textDp,
                    color = FColor.Secondary1Light,
                ),
            )
        },
    )
}

@Composable
private fun Step1Component(
    titleText: String,
    titleTextLimit: Int,
    currentCategories: List<Category>,
    deadlineDate: String,
    deadlineDateTagEnable: Boolean,
    recruitmentActor: String,
    recruitmentNumber: String,
    currentGender: Gender?,
    genderTagEnable: Boolean,
    defaultAgeRange: ClosedFloatingPointRange<Float>,
    currentAgeRange: ClosedFloatingPointRange<Float>,
    ageTagEnable: Boolean,
    currentCareer: Career?,
    careerTagEnable: Boolean,
    focusEvent: ActorRecruitingFocusEvent?,
    onUpdateTitleText: (String) -> Unit,
    onUpdateCategories: (Category, Boolean) -> Unit,
    onUpdateDeadlineDate: (String) -> Unit,
    onUpdateRecruitmentActor: (String) -> Unit,
    onUpdateRecruitmentNumber: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateDeadlineTag: (Boolean) -> Unit,
    onUpdateGenderTag: () -> Unit,
    onUpdateAgeReset: () -> Unit,
    onUpdateAgeRange: (ClosedFloatingPointRange<Float>) -> Unit,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerTag: (Boolean) -> Unit,
    recruitingRoleTextLimit: Int,
    recruitingMemberTextLimit: Int,
) {
    val titleFocusRequester = remember { FocusRequester() }
    val categoryFocusRequester = remember { FocusRequester() }
    val deadlineFocusRequester = remember { FocusRequester() }
    val recruitmentNumberFocusRequester = remember { FocusRequester() }

    LaunchedEffect(focusEvent) {
        when (focusEvent) {
            ActorRecruitingFocusEvent.Title -> titleFocusRequester.requestFocus()
            ActorRecruitingFocusEvent.Category -> categoryFocusRequester.requestFocus()
            ActorRecruitingFocusEvent.Deadline -> deadlineFocusRequester.requestFocus()
            ActorRecruitingFocusEvent.RecruitmentNumber -> recruitmentNumberFocusRequester.requestFocus()
            else -> Unit
        }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_step_1_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
        )

        Spacer(modifier = Modifier.height(9.dp))

        ContentTitleInputComponent(
            modifier = Modifier.focusRequester(titleFocusRequester),
            titleText = titleText,
            titleTextLimit = titleTextLimit,
            onUpdateTitleText = onUpdateTitleText,
        )

        Spacer(modifier = Modifier.height(20.dp))

        CategorySelectComponent(
            modifier = Modifier
                .focusRequester(categoryFocusRequester),
            currentCategories = currentCategories,
            onUpdateCategories = onUpdateCategories,
        )
    }

    Divider(thickness = 8.dp, color = FColor.Divider2)

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        RecruitmentInputComponent(
            deadlineDate = deadlineDate,
            recruitmentActor = recruitmentActor,
            recruitmentNumber = recruitmentNumber,
            currentGender = currentGender,
            deadlineDateTagEnable = deadlineDateTagEnable,
            genderTagEnable = genderTagEnable,
            modifier = Modifier
                .focusable()
                .focusRequester(deadlineFocusRequester),
            recruitmentNumberModifier = Modifier
                .focusable()
                .focusRequester(recruitmentNumberFocusRequester),
            onUpdateDeadlineDate = onUpdateDeadlineDate,
            onUpdateRecruitmentActor = onUpdateRecruitmentActor,
            onUpdateRecruitmentNumber = onUpdateRecruitmentNumber,
            onUpdateGender = onUpdateGender,
            onUpdateDeadlineTag = onUpdateDeadlineTag,
            onUpdateGenderTag = onUpdateGenderTag,
            recruitingRoleTextLimit = recruitingRoleTextLimit,
            recruitingMemberTextLimit = recruitingMemberTextLimit,
        )

        Spacer(modifier = Modifier.height(20.dp))

        AgeComponent(
            currentAgeRange = currentAgeRange,
            defaultAgeRange = defaultAgeRange,
            ageTagEnable = ageTagEnable,
            onUpdateAgeReset = onUpdateAgeReset,
            onUpdateAgeRange = onUpdateAgeRange,
        )
    }

    Divider(thickness = 8.dp, color = FColor.Divider2)

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        CareerInputComponent(
            currentCareer = currentCareer,
            careerTagEnable = careerTagEnable,
            onUpdateCareer = onUpdateCareer,
            onUpdateCareerTag = onUpdateCareerTag,
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step2Component(
    production: String,
    workTitle: String,
    directorName: String,
    genre: String,
    logLine: String,
    logLineTextLimit: Int,
    logLineTagEnable: Boolean,
    focusEvent: ActorRecruitingFocusEvent?,
    onUpdateProduction: (String) -> Unit,
    onUpdateWorkTitle: (String) -> Unit,
    onUpdateDirectorName: (String) -> Unit,
    onUpdateGenre: (String) -> Unit,
    onUpdateLogLine: (String) -> Unit,
    onUpdateLogLineTag: (Boolean) -> Unit,
    productionTextLimit: Int,
    workTitleTextLimit: Int,
    directorNameTextLimit: Int,
    genreTextLimit: Int,
) {
    val productionFocusRequester = remember { FocusRequester() }
    val workTitleFocusRequester = remember { FocusRequester() }
    val directorNameFocusRequester = remember { FocusRequester() }
    val genreFocusRequester = remember { FocusRequester() }

    LaunchedEffect(focusEvent) {
        when (focusEvent) {
            ActorRecruitingFocusEvent.Production -> productionFocusRequester.requestFocus()
            ActorRecruitingFocusEvent.WorkTitle -> workTitleFocusRequester.requestFocus()
            ActorRecruitingFocusEvent.DirectorName -> directorNameFocusRequester.requestFocus()
            ActorRecruitingFocusEvent.Genre -> genreFocusRequester.requestFocus()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_step_2_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
        )

        Spacer(modifier = Modifier.height(9.dp))

        LeftTitleTextField(
            modifier = Modifier.focusRequester(productionFocusRequester),
            title = stringResource(id = R.string.recruiting_register_actor_production_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_actor_production_placeholder),
            text = production,
            onValueChanged = onUpdateProduction,
            textLimit = productionTextLimit,
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            modifier = Modifier.focusRequester(workTitleFocusRequester),
            title = stringResource(id = R.string.recruiting_register_actor_work_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_actor_work_placeholder),
            text = workTitle,
            onValueChanged = onUpdateWorkTitle,
            textLimit = workTitleTextLimit,
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            modifier = Modifier.focusRequester(directorNameFocusRequester),
            title = stringResource(id = R.string.recruiting_register_actor_director_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_actor_director_placeholder),
            text = directorName,
            onValueChanged = onUpdateDirectorName,
            textLimit = directorNameTextLimit,
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            modifier = Modifier.focusRequester(genreFocusRequester),
            title = stringResource(id = R.string.recruiting_register_actor_genre_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_actor_genre_placeholder),
            text = genre,
            onValueChanged = onUpdateGenre,
            textLimit = genreTextLimit,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_log_line_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_log_line_private),
            isRequired = false,
            tagEnable = logLineTagEnable,
            onTagClick = {
                onUpdateLogLineTag(logLineTagEnable.not())
            },
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            text = logLine,
            onValueChange = onUpdateLogLine,
            fixedHeight = 134.dp,
            maxLines = Int.MAX_VALUE,
            singleLine = false,
            placeholder = stringResource(id = R.string.recruiting_register_actor_log_line_placeholder),
            bottomComponent = {
                TextLimitComponent(
                    modifier = Modifier
                        .align(Alignment.End),
                    currentTextSize = logLine.length,
                    maxTextSize = logLineTextLimit,
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Default,
                keyboardType = KeyboardType.Text,
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step3Component(
    modifier: Modifier = Modifier,
    location: String,
    period: String,
    pay: String,
    locationTagEnable: Boolean,
    periodTagEnable: Boolean,
    payTagEnable: Boolean,
    onUpdateLocationTag: (Boolean) -> Unit,
    onUpdatePeriodTag: (Boolean) -> Unit,
    onUpdatePayTag: (Boolean) -> Unit,
    onUpdateLocation: (String) -> Unit,
    onUpdatePeriod: (String) -> Unit,
    onUpdatePay: (String) -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_step_3_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_location_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_location_undetermined),
            isRequired = false,
            tagEnable = locationTagEnable,
            onTagClick = {
                onUpdateLocationTag(locationTagEnable.not())
            },
        )

        Spacer(modifier = Modifier.height(5.dp))

        FTextField(
            text = location,
            onValueChange = onUpdateLocation,
            placeholder = stringResource(id = R.string.recruiting_register_actor_location_placeholder),
            placeholderTextColor = if (locationTagEnable) {
                FColor.DisableBase
            } else {
                FColor.DisablePlaceholder
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_period_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_period_undetermined),
            isRequired = false,
            tagEnable = periodTagEnable,
            onTagClick = {
                onUpdatePeriodTag(periodTagEnable.not())
            },
        )

        Spacer(modifier = Modifier.height(5.dp))

        FTextField(
            text = period,
            onValueChange = onUpdatePeriod,
            placeholder = stringResource(id = R.string.recruiting_register_actor_period_placeholder),
            placeholderTextColor = if (periodTagEnable) {
                FColor.DisableBase
            } else {
                FColor.DisablePlaceholder
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_pay_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_pay_undetermined),
            isRequired = false,
            tagEnable = payTagEnable,
            onTagClick = {
                onUpdatePayTag(payTagEnable.not())
            },
        )

        Spacer(modifier = Modifier.height(5.dp))

        FTextField(
            text = pay,
            onValueChange = onUpdatePay,
            placeholder = stringResource(id = R.string.recruiting_register_actor_pay_placeholder),
            placeholderTextColor = if (payTagEnable) {
                FColor.DisableBase
            } else {
                FColor.DisablePlaceholder
            },
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step4Component(
    modifier: Modifier = Modifier,
    detailInfo: String,
    detailInfoTextLimit: Int,
    focusEvent: ActorRecruitingFocusEvent?,
    onUpdateDetailInfo: (String) -> Unit,
) {
    val detailFocusRequester = remember { FocusRequester() }

    LaunchedEffect(focusEvent) {
        when (focusEvent) {
            ActorRecruitingFocusEvent.Detail -> detailFocusRequester.requestFocus()
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .focusRequester(detailFocusRequester),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle()) {
                    append(stringResource(id = R.string.recruiting_register_actor_step_4_title))
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W500,
                        color = FColor.Error,
                    ),
                ) {
                    append("*")
                }
            },
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
            fontSize = 15.textDp,
            lineHeight = 20.textDp,
        )

        Spacer(modifier = Modifier.height(5.dp))

        FTextField(
            text = detailInfo,
            onValueChange = onUpdateDetailInfo,
            maxLines = Int.MAX_VALUE,
            singleLine = false,
            fixedHeight = 134.dp,
            placeholder = stringResource(id = R.string.recruiting_register_actor_step_4_placeholder),
            bottomComponent = {
                TextLimitComponent(
                    modifier = Modifier
                        .align(Alignment.End),
                    currentTextSize = detailInfo.length,
                    maxTextSize = detailInfoTextLimit,
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step5Component(
    modifier: Modifier = Modifier,
    manager: String,
    email: String,
    focusEvent: ActorRecruitingFocusEvent?,
    onUpdateManager: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onRegisterClick: () -> Unit,
    managerTextLimit: Int,
) {
    val managerFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }

    LaunchedEffect(focusEvent) {
        when (focusEvent) {
            ActorRecruitingFocusEvent.Manager -> managerFocusRequester.requestFocus()
            ActorRecruitingFocusEvent.Email -> emailFocusRequester.requestFocus()
            else -> Unit
        }
    }

    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_step_5_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
        )

        Spacer(modifier = Modifier.height(13.dp))

        LeftTitleTextField(
            modifier = Modifier.focusRequester(managerFocusRequester),
            title = stringResource(id = R.string.recruiting_register_actor_manager_title),
            titleSpace = 13,
            text = manager,
            onValueChanged = onUpdateManager,
            textLimit = managerTextLimit,
        )

        Spacer(modifier = Modifier.height(20.dp))

        LeftTitleTextField(
            modifier = Modifier.focusRequester(emailFocusRequester),
            title = stringResource(id = R.string.recruiting_register_actor_email_title),
            titleSpace = 13,
            text = email,
            onValueChanged = onUpdateEmail,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
            ),
        )

        Spacer(modifier = Modifier.height(56.dp))

        FButton(
            title = stringResource(id = R.string.recruiting_register_actor_register_button_title),
            enable = true,
            onClick = {
            },
            modifier = Modifier.clickableSingle {
                onRegisterClick()
            }
        )

        Spacer(modifier = Modifier.height(38.dp))
    }
}

@Composable
private fun CareerInputComponent(
    currentCareer: Career?,
    careerTagEnable: Boolean,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerTag: (Boolean) -> Unit,
) {
    TextWithRequiredTag(
        title = stringResource(id = R.string.recruiting_register_actor_career_title),
        tagTitle = stringResource(
            id = R.string.recruiting_register_actor_career_irrelevant,
        ),
        isRequired = false,
        tagEnable = careerTagEnable,
        onTagClick = {
            onUpdateCareerTag(careerTagEnable.not())
        },
    )

    Spacer(modifier = Modifier.height(6.dp))

    CareerTags(
        currentCareers = currentCareer,
        onUpdateCareer = onUpdateCareer,
    )
}

@Composable
private fun RecruitmentInputComponent(
    deadlineDate: String,
    deadlineDateTagEnable: Boolean,
    recruitmentActor: String,
    recruitmentNumber: String,
    currentGender: Gender?,
    genderTagEnable: Boolean,
    modifier: Modifier = Modifier,
    recruitmentNumberModifier: Modifier = Modifier,
    onUpdateDeadlineDate: (String) -> Unit,
    onUpdateDeadlineTag: (Boolean) -> Unit,
    onUpdateRecruitmentActor: (String) -> Unit,
    onUpdateRecruitmentNumber: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderTag: () -> Unit,
    recruitingRoleTextLimit: Int,
    recruitingMemberTextLimit: Int,
) {
    TextWithRequiredTag(
        title = stringResource(id = R.string.recruiting_register_actor_deadline_title),
        tagTitle = stringResource(id = R.string.recruiting_register_actor_always_recruiting),
        isRequired = true,
        tagEnable = deadlineDateTagEnable,
        onTagClick = {
            onUpdateDeadlineTag(deadlineDateTagEnable.not())
        },
    )

    Spacer(modifier = Modifier.height(6.dp))

    FTextField(
        modifier = modifier,
        text = deadlineDate,
        placeholder = stringResource(id = R.string.recruiting_register_actor_deadline_placeholder),
        placeholderTextColor = if (deadlineDateTagEnable) {
            FColor.DisableBase
        } else {
            FColor.DisablePlaceholder
        },
        onValueChange = onUpdateDeadlineDate,
        pattern = Pattern.compile(PatternUtil.dateRegex),
        onTextChanged = { before, after ->
            if (before.text.length < after.text.length) {
                when (after.text.length) {
                    5, 8 -> after.copy(
                        text = "${before.text}-${after.text.last()}",
                        selection = TextRange(after.text.length + 1),
                    )

                    else -> after
                }
            } else {
                when (after.text.length) {
                    5, 8 -> after.copy(
                        text = after.text.dropLast(1),
                    )

                    else -> after
                }
            }
        },
        textLimit = 10,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
    )

    Spacer(modifier = Modifier.height(20.dp))

    FTextField(
        text = recruitmentActor,
        onValueChange = onUpdateRecruitmentActor,
        leftComponents = {
            Text(
                text = stringResource(id = R.string.recruiting_register_actor_target_role_title),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 15.textDp,
                    lineHeight = 20.textDp,
                    color = FColor.TextPrimary,
                ),
            )

            Spacer(modifier = Modifier.width(19.dp))
        },
        placeholder = stringResource(id = R.string.recruiting_register_actor_target_role_placeholder),
        textLimit = recruitingRoleTextLimit,
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextWithRequiredTag(
        title = stringResource(id = R.string.recruiting_register_actor_target_personal),
        tagTitle = stringResource(id = R.string.recruiting_register_actor_gender_irrelevant),
        isRequired = true,
        tagEnable = genderTagEnable,
        onTagClick = {
            onUpdateGenderTag()
        },
    )

    Spacer(modifier = Modifier.height(6.dp))

    RecruitmentNumberInputComponent(
        modifier = recruitmentNumberModifier,
        recruitmentNumber = recruitmentNumber,
        currentGender = currentGender,
        onUpdateRecruitmentNumber = onUpdateRecruitmentNumber,
        onUpdateGender = onUpdateGender,
        recruitingMemberTextLimit = recruitingMemberTextLimit,
    )
}

@Composable
private fun CategorySelectComponent(
    modifier: Modifier = Modifier,
    currentCategories: List<Category>,
    onUpdateCategories: (Category, Boolean) -> Unit,
) {
    Row(
        modifier = modifier.focusable(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextWithRequired(
            title = stringResource(id = R.string.profile_register_category_title),
            isRequired = true,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_category_subtitle),
            style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 12.textDp,
                lineHeight = 17.textDp,
                color = FColor.DisablePlaceholder,
            ),
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    CategoryTags(
        currentCategories = currentCategories,
        onUpdateCategories = onUpdateCategories,
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun ContentTitleInputComponent(
    modifier: Modifier = Modifier,
    titleText: String,
    titleTextLimit: Int,
    onUpdateTitleText: (String) -> Unit,
) {
    Column(modifier = modifier) {
        TextWithRequired(
            title = stringResource(id = R.string.recruiting_register_actor_title),
            isRequired = true,
        )

        Spacer(modifier = Modifier.height(8.dp))

        FTextField(
            text = titleText,
            textLimit = titleTextLimit,
            onValueChange = onUpdateTitleText,
            tailComponent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp),
                ) {
                    TextLimitComponent(
                        currentTextSize = titleText.length,
                        maxTextSize = titleTextLimit,
                    )
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AgeComponent(
    modifier: Modifier = Modifier,
    currentAgeRange: ClosedFloatingPointRange<Float>,
    defaultAgeRange: ClosedFloatingPointRange<Float>,
    ageTagEnable: Boolean,
    onUpdateAgeReset: () -> Unit,
    onUpdateAgeRange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_age_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_age_irrelevant),
            isRequired = false,
            tagEnable = ageTagEnable,
            onTagClick = onUpdateAgeReset,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(
                if (currentAgeRange.endInclusive >= 70f) {
                    R.string.recruiting_register_actor_age_range
                } else {
                    R.string.recruiting_register_actor_age_range_no_limit
                },
                currentAgeRange.start.toInt(),
                currentAgeRange.endInclusive.toInt(),
            ),
            style = LocalTypography.current.b2(),
            color = FColor.TextSecondary,
        )

        RangeSlider(
            modifier = Modifier.padding(0.dp),
            value = currentAgeRange,
            onValueChange = onUpdateAgeRange,
            valueRange = defaultAgeRange,
            steps = 70,
            colors = SliderDefaults.colors(
                thumbColor = FColor.Primary,
                activeTrackColor = FColor.Primary,
                inactiveTrackColor = FColor.DisablePlaceholder,
                activeTickColor = FColor.Transparent,
                inactiveTickColor = FColor.Transparent,
            ),
        )
    }
}

@Composable
private fun RecruitmentNumberInputComponent(
    modifier: Modifier = Modifier,
    recruitmentNumber: String,
    currentGender: Gender?,
    onUpdateRecruitmentNumber: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    recruitingMemberTextLimit: Int,
) {
    FTextField(
        modifier = modifier,
        text = recruitmentNumber,
        onValueChange = onUpdateRecruitmentNumber,
        rightComponents = {
            Spacer(modifier = Modifier.width(8.dp))

            FBorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_gender_man),
                enable = currentGender == Gender.MAN,
                onClick = {
                    onUpdateGender(Gender.MAN, (currentGender == Gender.MAN).not())
                },
            )

            Spacer(modifier = Modifier.width(6.dp))

            FBorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_gender_woman),
                enable = currentGender == Gender.WOMAN,
                onClick = {
                    onUpdateGender(Gender.WOMAN, (currentGender == Gender.WOMAN).not())
                },
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        textLimit = recruitingMemberTextLimit
    )
}
