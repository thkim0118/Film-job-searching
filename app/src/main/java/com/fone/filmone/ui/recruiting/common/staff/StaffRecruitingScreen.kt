package com.fone.filmone.ui.recruiting.common.staff

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.FBorderButton
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.tag.career.CareerTags
import com.fone.filmone.ui.common.tag.categories.CategoryTags
import com.fone.filmone.ui.main.job.common.DomainInputComponent
import com.fone.filmone.ui.main.job.common.LeftTitleTextField
import com.fone.filmone.ui.main.job.common.TextLimitComponent
import com.fone.filmone.ui.main.job.common.TextWithRequired
import com.fone.filmone.ui.main.job.common.TextWithRequiredTag
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingDialogState
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingUiEvent
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingUiModel
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import java.util.regex.Pattern

@Composable
fun StaffRecruitingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: StaffRecruitingUiModel,
    dialogState: StaffRecruitingDialogState,
    baseViewModel: BaseViewModel,
    onRegisterClick: () -> Unit,
    onUpdateTitleText: (String) -> Unit,
    onUpdateCategories: (Category, Boolean) -> Unit,
    onUpdateDeadlineDate: (String) -> Unit,
    onUpdateDeadlineTag: (Boolean) -> Unit,
    onUpdateDomains: () -> Unit,
    onUpdateRecruitmentNumber: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderTag: () -> Unit,
    onUpdateAgeRange: (ClosedFloatingPointRange<Float>) -> Unit,
    onUpdateAgeTag: (Boolean) -> Unit,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerTag: (Boolean) -> Unit,
    onUpdateProduction: (String) -> Unit,
    onUpdateWorkTitle: (String) -> Unit,
    onUpdateDirectorName: (String) -> Unit,
    onUpdateGenre: (String) -> Unit,
    onUpdateLogLine: (String) -> Unit,
    onLogLineTagClick: () -> Unit,
    onUpdateLocationTag: (Boolean) -> Unit,
    onUpdatePeriodTag: (Boolean) -> Unit,
    onUpdatePayTag: (Boolean) -> Unit,
    onUpdateLocation: (String) -> Unit,
    onUpdatePeriod: (String) -> Unit,
    onUpdatePay: (String) -> Unit,
    onUpdateDetailInfo: (String) -> Unit,
    onUpdateManager: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onDismiss: (List<Domain>) -> Unit,
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = uiState.staffRecruitingRegisterUiEvent) {
        listenUiEvent(uiState.staffRecruitingRegisterUiEvent, navController)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = baseViewModel, hostState = it)
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Column(
                modifier = Modifier,
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
                        titleText = uiState.staffRecruitingStep1UiModel.titleText,
                        titleTextLimit = uiState.staffRecruitingStep1UiModel.titleTextLimit,
                        currentCategories = uiState.staffRecruitingStep1UiModel.categories.toList(),
                        deadlineDate = uiState.staffRecruitingStep1UiModel.deadlineDate,
                        deadlineTagEnable = uiState.staffRecruitingStep1UiModel.deadlineTagEnable,
                        selectedDomains = uiState.staffRecruitingStep1UiModel.recruitmentDomains.toList(),
                        recruitmentNumber = uiState.staffRecruitingStep1UiModel.recruitmentNumber,
                        currentGenders = uiState.staffRecruitingStep1UiModel.recruitmentGender
                            ?: Gender.IRRELEVANT,
                        genderTagEnable = uiState.staffRecruitingStep1UiModel.genderTagEnable,
                        defaultAgeRange = uiState.staffRecruitingStep1UiModel.defaultAgeRange,
                        currentAgeRange = uiState.staffRecruitingStep1UiModel.ageRange,
                        ageTagEnable = uiState.staffRecruitingStep1UiModel.ageTagEnable,
                        currentCareers = uiState.staffRecruitingStep1UiModel.career,
                        careerTagEnable = uiState.staffRecruitingStep1UiModel.careerTagEnable,
                        onUpdateTitleText = onUpdateTitleText,
                        onUpdateCategories = onUpdateCategories,
                        onUpdateDeadlineDate = onUpdateDeadlineDate,
                        onUpdateDeadlineTag = onUpdateDeadlineTag,
                        onUpdateDomains = onUpdateDomains,
                        onUpdateRecruitmentNumber = onUpdateRecruitmentNumber,
                        onUpdateGender = onUpdateGender,
                        onUpdateGenderTag = onUpdateGenderTag,
                        onUpdateAgeRange = onUpdateAgeRange,
                        onUpdateAgeTag = onUpdateAgeTag,
                        onUpdateCareer = onUpdateCareer,
                        onUpdateCareerTag = onUpdateCareerTag,
                    )

                    Divider(thickness = 8.dp, color = FColor.Divider2)

                    Step2Component(
                        production = uiState.staffRecruitingStep2UiModel.production,
                        workTitle = uiState.staffRecruitingStep2UiModel.workTitle,
                        directorName = uiState.staffRecruitingStep2UiModel.directorName,
                        genre = uiState.staffRecruitingStep2UiModel.genre,
                        logLine = uiState.staffRecruitingStep2UiModel.logLine,
                        logLineTextLimit = uiState.staffRecruitingStep2UiModel.logLineTextLimit,
                        isLogLinePrivate = uiState.staffRecruitingStep2UiModel.isLogLinePrivate,
                        onUpdateProduction = onUpdateProduction,
                        onUpdateWorkTitle = onUpdateWorkTitle,
                        onUpdateDirectorName = onUpdateDirectorName,
                        onUpdateGenre = onUpdateGenre,
                        onUpdateLogLine = onUpdateLogLine,
                        onLogLineTagClick = onLogLineTagClick,
                    )

                    Divider(thickness = 8.dp, color = FColor.Divider2)

                    Step3Component(
                        location = uiState.staffRecruitingStep3UiModel.location,
                        period = uiState.staffRecruitingStep3UiModel.period,
                        pay = uiState.staffRecruitingStep3UiModel.pay,
                        locationTagEnable = uiState.staffRecruitingStep3UiModel.locationTagEnable,
                        periodTagEnable = uiState.staffRecruitingStep3UiModel.periodTagEnable,
                        payTagEnable = uiState.staffRecruitingStep3UiModel.payTagEnable,
                        onUpdateLocationTag = onUpdateLocationTag,
                        onUpdatePeriodTag = onUpdatePeriodTag,
                        onUpdatePayTag = onUpdatePayTag,
                        onUpdateLocation = onUpdateLocation,
                        onUpdatePeriod = onUpdatePeriod,
                        onUpdatePay = onUpdatePay,
                    )

                    Divider(thickness = 8.dp, color = FColor.Divider2)

                    Step4Component(
                        detailInfo = uiState.staffRecruitingStep4UiModel.detailInfo,
                        detailInfoTextLimit = uiState.staffRecruitingStep4UiModel.detailInfoTextLimit,
                        onUpdateDetailInfo = onUpdateDetailInfo,
                    )

                    Divider(thickness = 8.dp, color = FColor.Divider2)

                    Step5Component(
                        manager = uiState.staffRecruitingStep5UiModel.manager,
                        email = uiState.staffRecruitingStep5UiModel.email,
                        registerButtonEnable = uiState.registerButtonEnable,
                        onUpdateManager = onUpdateManager,
                        onUpdateEmail = onUpdateEmail,
                        onRegisterClick = onRegisterClick,
                    )
                }
            }

            DialogScreen(
                dialogState = dialogState,
                onDismiss = onDismiss,
            )
        }
    }
}

private fun listenUiEvent(
    uiEvent: StaffRecruitingUiEvent,
    navController: NavHostController,
) {
    when (uiEvent) {
        StaffRecruitingUiEvent.Clear -> Unit
        StaffRecruitingUiEvent.RegisterComplete -> {
            navController.popBackStack()
        }
    }
}

@Composable
private fun TitleComponent(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    FTitleBar(
        titleText = stringResource(id = R.string.recruiting_register_staff_title_text),
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
                text = stringResource(id = R.string.recruiting_register_staff_title_right_button),
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
    deadlineTagEnable: Boolean,
    selectedDomains: List<Domain>,
    recruitmentNumber: String,
    currentGenders: Gender,
    genderTagEnable: Boolean,
    defaultAgeRange: ClosedFloatingPointRange<Float>,
    currentAgeRange: ClosedFloatingPointRange<Float>,
    ageTagEnable: Boolean,
    currentCareers: Career?,
    careerTagEnable: Boolean,
    onUpdateTitleText: (String) -> Unit,
    onUpdateCategories: (Category, Boolean) -> Unit,
    onUpdateDeadlineDate: (String) -> Unit,
    onUpdateDeadlineTag: (Boolean) -> Unit,
    onUpdateDomains: () -> Unit,
    onUpdateRecruitmentNumber: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderTag: () -> Unit,
    onUpdateAgeRange: (ClosedFloatingPointRange<Float>) -> Unit,
    onUpdateAgeTag: (Boolean) -> Unit,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerTag: (Boolean) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_staff_step_1_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
        )

        Spacer(modifier = Modifier.height(9.dp))

        ContentTitleInputComponent(
            titleText = titleText,
            titleTextLimit = titleTextLimit,
            onUpdateTitleText = onUpdateTitleText,
        )

        Spacer(modifier = Modifier.height(20.dp))

        CategorySelectComponent(
            currentCategories = currentCategories,
            onUpdateCategories = onUpdateCategories,
        )
    }

    Divider(thickness = 8.dp, color = FColor.Divider2)

    Spacer(modifier = Modifier.height(20.dp))

    RecruitmentInputComponent(
        deadlineDate = deadlineDate,
        deadlineTagEnable = deadlineTagEnable,
        selectedDomains = selectedDomains,
        recruitmentNumber = recruitmentNumber,
        currentGenders = currentGenders,
        genderTagEnable = genderTagEnable,
        onUpdateDeadlineDate = onUpdateDeadlineDate,
        onUpdateDeadlineTag = onUpdateDeadlineTag,
        onUpdateDomains = onUpdateDomains,
        onUpdateRecruitmentNumber = onUpdateRecruitmentNumber,
        onUpdateGender = onUpdateGender,
        onUpdateGenderTag = onUpdateGenderTag,
    )

    Spacer(modifier = Modifier.height(20.dp))

    AgeComponent(
        currentAgeRange = currentAgeRange,
        ageTagEnable = ageTagEnable,
        defaultAgeRange = defaultAgeRange,
        onUpdateAgeRange = onUpdateAgeRange,
        onUpdateAgeTag = onUpdateAgeTag,
    )

    Divider(thickness = 8.dp, color = FColor.Divider2)

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        CareerInputComponent(
            currentCareers = currentCareers,
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
    isLogLinePrivate: Boolean,
    onUpdateProduction: (String) -> Unit,
    onUpdateWorkTitle: (String) -> Unit,
    onUpdateDirectorName: (String) -> Unit,
    onUpdateGenre: (String) -> Unit,
    onUpdateLogLine: (String) -> Unit,
    onLogLineTagClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_staff_step_2_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
        )

        Spacer(modifier = Modifier.height(9.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_staff_production_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_staff_production_placeholder),
            text = production,
            onValueChanged = onUpdateProduction,
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_staff_work_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_staff_work_placeholder),
            text = workTitle,
            onValueChanged = onUpdateWorkTitle,
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_staff_director_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_staff_director_placeholder),
            text = directorName,
            onValueChanged = onUpdateDirectorName,
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_staff_genre_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_staff_genre_placeholder),
            text = genre,
            onValueChanged = onUpdateGenre,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_staff_log_line_title),
            tagTitle = stringResource(id = R.string.recruiting_register_staff_log_line_private),
            isRequired = false,
            tagEnable = isLogLinePrivate,
            onTagClick = onLogLineTagClick,
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            text = logLine,
            onValueChange = onUpdateLogLine,
            fixedHeight = 134.dp,
            placeholder = stringResource(id = R.string.recruiting_register_staff_log_line_placeholder),
            textLimit = logLineTextLimit,
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
            text = stringResource(id = R.string.recruiting_register_staff_step_3_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_staff_location_title),
            tagTitle = stringResource(id = R.string.recruiting_register_staff_location_undetermined),
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
            placeholder = stringResource(id = R.string.recruiting_register_staff_location_placeholder),
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_staff_period_title),
            tagTitle = stringResource(id = R.string.recruiting_register_staff_period_undetermined),
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
            placeholder = stringResource(id = R.string.recruiting_register_staff_period_placeholder),
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_staff_pay_title),
            tagTitle = stringResource(id = R.string.recruiting_register_staff_pay_undetermined),
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
            placeholder = stringResource(id = R.string.recruiting_register_staff_pay_placeholder),
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step4Component(
    modifier: Modifier = Modifier,
    detailInfo: String,
    detailInfoTextLimit: Int,
    onUpdateDetailInfo: (String) -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle()) {
                    append(stringResource(id = R.string.recruiting_register_staff_step_4_title))
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
            textLimit = detailInfoTextLimit,
            fixedHeight = 134.dp,
            placeholder = stringResource(id = R.string.recruiting_register_staff_step_4_placeholder),
            bottomComponent = {
                TextLimitComponent(
                    modifier = Modifier
                        .align(Alignment.End),
                    currentTextSize = detailInfo.length,
                    maxTextSize = detailInfoTextLimit,
                )
            },
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step5Component(
    modifier: Modifier = Modifier,
    manager: String,
    email: String,
    registerButtonEnable: Boolean,
    onUpdateManager: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_staff_step_5_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
        )

        Spacer(modifier = Modifier.height(13.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_staff_manager_title),
            titleSpace = 13,
            text = manager,
            onValueChanged = onUpdateManager,
        )

        Spacer(modifier = Modifier.height(20.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_staff_email_title),
            titleSpace = 13,
            text = email,
            onValueChanged = onUpdateEmail,
        )

        Spacer(modifier = Modifier.height(56.dp))

        FButton(
            title = stringResource(id = R.string.recruiting_register_staff_register_button_title),
            enable = registerButtonEnable,
            onClick = {
                if (registerButtonEnable) {
                    onRegisterClick()
                }
            },
        )

        Spacer(modifier = Modifier.height(38.dp))
    }
}

@Composable
private fun CareerInputComponent(
    currentCareers: Career?,
    careerTagEnable: Boolean,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerTag: (Boolean) -> Unit,
) {
    TextWithRequiredTag(
        title = stringResource(id = R.string.recruiting_register_staff_career_title),
        tagTitle = stringResource(
            id = R.string.recruiting_register_staff_career_irrelevant,
        ),
        isRequired = false,
        tagEnable = careerTagEnable,
        onTagClick = {
            onUpdateCareerTag(careerTagEnable.not())
        },
    )

    Spacer(modifier = Modifier.height(6.dp))

    CareerTags(
        currentCareers = currentCareers,
        onUpdateCareer = onUpdateCareer,
    )
}

@Composable
private fun RecruitmentInputComponent(
    deadlineDate: String,
    deadlineTagEnable: Boolean,
    selectedDomains: List<Domain>,
    recruitmentNumber: String,
    currentGenders: Gender,
    genderTagEnable: Boolean,
    onUpdateDeadlineDate: (String) -> Unit,
    onUpdateDeadlineTag: (Boolean) -> Unit,
    onUpdateDomains: () -> Unit,
    onUpdateRecruitmentNumber: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderTag: () -> Unit,
) {
    TextWithRequiredTag(
        modifier = Modifier.padding(horizontal = 20.dp),
        title = stringResource(id = R.string.recruiting_register_staff_deadline_title),
        tagTitle = stringResource(id = R.string.recruiting_register_staff_always_recruiting),
        isRequired = true,
        tagEnable = deadlineTagEnable,
        onTagClick = {
            onUpdateDeadlineTag(deadlineTagEnable.not())
        },
    )

    Spacer(modifier = Modifier.height(6.dp))

    FTextField(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = deadlineDate,
        placeholder = stringResource(id = R.string.recruiting_register_staff_deadline_placeholder),
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

    DomainInputComponent(
        selectedDomains = selectedDomains,
        onUpdateDomains = onUpdateDomains,
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextWithRequiredTag(
        modifier = Modifier.padding(horizontal = 20.dp),
        title = stringResource(id = R.string.recruiting_register_staff_target_personal),
        tagTitle = stringResource(id = R.string.recruiting_register_staff_gender_irrelevant),
        isRequired = true,
        tagEnable = genderTagEnable,
        onTagClick = {
            onUpdateGenderTag()
        },
    )

    Spacer(modifier = Modifier.height(6.dp))

    FTextField(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = recruitmentNumber,
        onValueChange = onUpdateRecruitmentNumber,
        rightComponents = {
            Spacer(modifier = Modifier.width(8.dp))

            FBorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_gender_man),
                enable = currentGenders == Gender.MAN,
                onClick = {
                    onUpdateGender(Gender.MAN, currentGenders != Gender.MAN)
                },
            )

            Spacer(modifier = Modifier.width(6.dp))

            FBorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_gender_woman),
                enable = currentGenders == Gender.WOMAN,
                onClick = {
                    onUpdateGender(Gender.WOMAN, currentGenders != Gender.WOMAN)
                },
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
    )
}

@Composable
private fun CategorySelectComponent(
    currentCategories: List<Category>,
    onUpdateCategories: (Category, Boolean) -> Unit,
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextWithRequired(
            title = stringResource(id = R.string.profile_register_category_title),
            isRequired = true,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_staff_category_subtitle),
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
    titleText: String,
    titleTextLimit: Int,
    onUpdateTitleText: (String) -> Unit,
) {
    TextWithRequired(
        title = stringResource(id = R.string.recruiting_register_staff_title),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AgeComponent(
    modifier: Modifier = Modifier,
    currentAgeRange: ClosedFloatingPointRange<Float>,
    ageTagEnable: Boolean,
    defaultAgeRange: ClosedFloatingPointRange<Float>,
    onUpdateAgeRange: (ClosedFloatingPointRange<Float>) -> Unit,
    onUpdateAgeTag: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_staff_age_title),
            tagTitle = stringResource(id = R.string.recruiting_register_staff_age_irrelevant),
            isRequired = false,
            tagEnable = ageTagEnable,
            onTagClick = {
                onUpdateAgeTag(ageTagEnable.not())
            },
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(
                if (currentAgeRange.endInclusive >= 70f) {
                    R.string.recruiting_register_staff_age_range
                } else {
                    R.string.recruiting_register_staff_age_range_no_limit
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
private fun DialogScreen(
    dialogState: StaffRecruitingDialogState,
    onDismiss: (List<Domain>) -> Unit,
) {
    when (dialogState) {
        StaffRecruitingDialogState.Clear -> Unit
        is StaffRecruitingDialogState.DomainSelectDialog -> {
            DomainSelectDialog(
                selectedDomains = dialogState.selectedDomains,
                onDismissRequest = { domains ->
                    onDismiss(domains)
                },
            )
        }
    }
}
