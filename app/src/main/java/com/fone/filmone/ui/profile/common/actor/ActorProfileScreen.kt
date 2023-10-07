package com.fone.filmone.ui.profile.common.actor

import androidx.compose.foundation.Image
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
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
import com.fone.filmone.ui.main.job.common.LeftTitleTextField
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileFocusEvent
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileUiEvent
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileUiModel
import com.fone.filmone.ui.profile.common.component.AbilityInputComponent
import com.fone.filmone.ui.profile.common.component.BirthdayInputComponent
import com.fone.filmone.ui.profile.common.component.CareerInputComponent
import com.fone.filmone.ui.profile.common.component.CategorySelectComponent
import com.fone.filmone.ui.profile.common.component.DetailInputComponent
import com.fone.filmone.ui.profile.common.component.EmailInputComponent
import com.fone.filmone.ui.profile.common.component.HookingCommentsComponent
import com.fone.filmone.ui.profile.common.component.NameInputComponent
import com.fone.filmone.ui.profile.common.component.PictureComponent
import com.fone.filmone.ui.profile.common.component.RegisterButton
import com.fone.filmone.ui.profile.common.component.SnsInputComponent
import com.fone.filmone.ui.profile.register.actor.ActorProfileRegisterScreen
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ActorProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: ActorProfileUiModel,
    baseViewModel: BaseViewModel,
    onRegisterClick: () -> Unit,
    onUpdateProfileImage: (String, Boolean) -> Unit,
    onRemoveImage: (String, Boolean) -> Unit,
    onNameChanged: (String) -> Unit,
    onUpdateBirthday: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderTag: (Boolean) -> Unit,
    onUpdateComments: (String) -> Unit,
    onUpdateHeight: (String) -> Unit,
    onUpdateWeight: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdateAbility: (String) -> Unit,
    onUpdateSns: (String) -> Unit,
    onUpdateDetailInfo: (String) -> Unit,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerDetail: (String) -> Unit,
    onCategoryTagClick: (Boolean) -> Unit,
    onUpdateCategory: (Category, Boolean) -> Unit,
    onRegisterButtonClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusEvent = uiState.focusEvent
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = uiState.actorProfileUiEvent) {
        listenUiEvent(
            uiEvent = uiState.actorProfileUiEvent,
            navController = navController
        )
    }

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = baseViewModel, hostState = it)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickableWithNoRipple {
                    keyboardController?.hide()
                }
                .padding(it)
        ) {
            TitleComponent(
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterClick = onRegisterClick
            )

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                PictureComponent(
                    pictureList = uiState.pictureList,
                    limit = 9,
                    onUpdateProfileImage = { encodedString ->
                        onUpdateProfileImage(encodedString, false)
                    },
                    onRemoveImage = { encodedString ->
                        onRemoveImage(encodedString, true)
                    },
                    actorFocusEvent = focusEvent,
                    staffFocusEvent = null,
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                UserInfoInputComponent(
                    name = uiState.name,
                    birthday = uiState.birthday,
                    genderTagEnable = uiState.genderTagEnable,
                    currentGender = uiState.gender,
                    hookingComments = uiState.hookingComments,
                    commentsTextLimit = uiState.commentsTextLimit,
                    height = uiState.height,
                    weight = uiState.weight,
                    email = uiState.email,
                    ability = uiState.specialty,
                    sns = uiState.sns,
                    onNameChanged = onNameChanged,
                    onUpdateBirthday = onUpdateBirthday,
                    onUpdateGender = onUpdateGender,
                    onUpdateGenderTag = onUpdateGenderTag,
                    onUpdateComments = onUpdateComments,
                    onUpdateHeight = onUpdateHeight,
                    onUpdateWeight = onUpdateWeight,
                    onUpdateEmail = onUpdateEmail,
                    onUpdateAbility = onUpdateAbility,
                    onUpdateSns = onUpdateSns,
                    focusEvent = focusEvent,
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                DetailInputComponent(
                    detailInfo = uiState.detailInfo,
                    detailInfoTextLimit = uiState.detailInfoTextLimit,
                    onUpdateDetailInfo = onUpdateDetailInfo,
                    actorFocusEvent = focusEvent,
                    staffFocusEvent = null,
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                CareerInputComponent(
                    currentCareer = uiState.career,
                    careerDetail = uiState.careerDetail,
                    careerDetailTextLimit = uiState.careerDetailTextLimit,
                    onUpdateCareer = onUpdateCareer,
                    onUpdateCareerDetail = onUpdateCareerDetail,
                    actorFocusEvent = focusEvent,
                    staffFocusEvent = null,
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                CategorySelectComponent(
                    categoryTagEnable = uiState.categoryTagEnable,
                    currentCategory = uiState.categories.toList(),
                    onCategoryTagClick = onCategoryTagClick,
                    onUpdateCategory = onUpdateCategory
                )

                RegisterButton(
                    buttonEnable = uiState.registerButtonEnable,
                    onRegisterButtonClick = onRegisterButtonClick
                )
            }
        }
    }
}

private fun listenUiEvent(
    uiEvent: ActorProfileUiEvent,
    navController: NavHostController,
) {
    when (uiEvent) {
        ActorProfileUiEvent.Clear -> Unit
        ActorProfileUiEvent.RegisterComplete -> {
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
        titleText = stringResource(id = R.string.profile_register_actor_title_text),
        leading = {
            Image(
                modifier = Modifier
                    .clickableSingleWithNoRipple { onBackClick() },
                imageVector = ImageVector.vectorResource(id = R.drawable.title_bar_back),
                contentDescription = null
            )
        },
        action = {
            Text(
                modifier = Modifier
                    .clickableSingle { onRegisterClick() },
                text = stringResource(id = R.string.profile_register_title_right_button),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 15.textDp,
                    lineHeight = 20.textDp,
                    color = FColor.Secondary1Light
                )
            )
        }
    )
}

@Composable
private fun UserInfoInputComponent(
    modifier: Modifier = Modifier,
    name: String,
    birthday: String,
    genderTagEnable: Boolean,
    currentGender: Gender?,
    hookingComments: String,
    commentsTextLimit: Int,
    height: String,
    weight: String,
    email: String,
    ability: String,
    sns: String,
    onNameChanged: (String) -> Unit,
    onUpdateBirthday: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderTag: (Boolean) -> Unit,
    onUpdateComments: (String) -> Unit,
    onUpdateHeight: (String) -> Unit,
    onUpdateWeight: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdateAbility: (String) -> Unit,
    onUpdateSns: (String) -> Unit,
    focusEvent: ActorProfileFocusEvent?,
) {
    val nameFocusRequester = remember { FocusRequester() }
    val hookingCommentRequester = remember { FocusRequester() }
    val birthdayRequester = remember { FocusRequester() }
    val heightRequester = remember { FocusRequester() }
    val weightRequester = remember { FocusRequester() }
    val emailRequester = remember { FocusRequester() }

    LaunchedEffect(focusEvent) {
        when (focusEvent) {
            ActorProfileFocusEvent.Name -> nameFocusRequester.requestFocus()
            ActorProfileFocusEvent.HookingComment -> hookingCommentRequester.requestFocus()
            ActorProfileFocusEvent.Birthday -> birthdayRequester.requestFocus()
            ActorProfileFocusEvent.Height -> heightRequester.requestFocus()
            ActorProfileFocusEvent.Weight -> weightRequester.requestFocus()
            ActorProfileFocusEvent.Email -> emailRequester.requestFocus()
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        NameInputComponent(
            name = name,
            onNameChanged = onNameChanged,
            modifier = Modifier.focusRequester(nameFocusRequester)
        )

        Spacer(modifier = Modifier.height(20.dp))

        HookingCommentsComponent(
            hookingComments = hookingComments,
            commentsTextLimit = commentsTextLimit,
            onUpdateComments = onUpdateComments,
            modifier = Modifier.focusRequester(hookingCommentRequester)
        )

        Spacer(modifier = Modifier.height(20.dp))

        BirthdayInputComponent(
            birthday = birthday,
            genderTagEnable = genderTagEnable,
            currentGender = currentGender,
            onUpdateBirthday = onUpdateBirthday,
            onUpdateGender = onUpdateGender,
            onUpdateGenderTag = onUpdateGenderTag,
            modifier = Modifier.focusRequester(birthdayRequester)
        )

        Spacer(modifier = Modifier.height(20.dp))

        HeightWeightInputComponent(
            height = height,
            weight = weight,
            onUpdateHeight = onUpdateHeight,
            onUpdateWeight = onUpdateWeight,
            heightModifier = Modifier.focusRequester(heightRequester),
            weightModifier = Modifier.focusRequester(weightRequester),
        )

        Spacer(modifier = Modifier.height(20.dp))

        EmailInputComponent(
            email = email,
            onUpdateEmail = onUpdateEmail,
            modifier = Modifier.focusRequester(emailRequester)
        )

        Spacer(modifier = Modifier.height(20.dp))

        AbilityInputComponent(ability = ability, onUpdateAbility = onUpdateAbility)

        Spacer(modifier = Modifier.height(20.dp))

        SnsInputComponent(sns = sns, onUpdateSns = onUpdateSns)

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun HeightWeightInputComponent(
    modifier: Modifier = Modifier,
    height: String,
    weight: String,
    onUpdateHeight: (String) -> Unit,
    onUpdateWeight: (String) -> Unit,
    heightModifier: Modifier,
    weightModifier: Modifier,
) {
    val rightComponentTextStyle = fTextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 12.textDp,
        lineHeight = 17.textDp,
        color = FColor.DisablePlaceholder
    )

    Row(
        modifier = modifier

    ) {
        LeftTitleTextField(
            modifier = heightModifier.weight(1f),
            title = stringResource(id = R.string.profile_register_height_title),
            titleSpace = 12,
            text = height,
            tailComponent = {
                Text(
                    text = stringResource(id = R.string.profile_register_height_postfix),
                    style = rightComponentTextStyle
                )
            },
            onValueChanged = onUpdateHeight,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.width(34.dp))

        LeftTitleTextField(
            modifier = weightModifier.weight(1f),
            title = stringResource(id = R.string.profile_register_weight_title),
            titleSpace = 12,
            text = weight,
            tailComponent = {
                Text(
                    text = stringResource(id = R.string.profile_register_weight_postfix),
                    style = rightComponentTextStyle
                )
            },
            onValueChanged = onUpdateWeight,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActorProfileRegisterScreenPreview() {
    FilmOneTheme {
        ActorProfileRegisterScreen()
    }
}
