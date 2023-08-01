package com.fone.filmone.ui.profile.register.actor

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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
import com.fone.filmone.ui.main.job.common.LeftTitleTextField
import com.fone.filmone.ui.profile.register.AbilityInputComponent
import com.fone.filmone.ui.profile.register.BirthdayInputComponent
import com.fone.filmone.ui.profile.register.CareerInputComponent
import com.fone.filmone.ui.profile.register.CategorySelectComponent
import com.fone.filmone.ui.profile.register.DetailInputComponent
import com.fone.filmone.ui.profile.register.EmailInputComponent
import com.fone.filmone.ui.profile.register.HookingCommentsComponent
import com.fone.filmone.ui.profile.register.NameInputComponent
import com.fone.filmone.ui.profile.register.PictureComponent
import com.fone.filmone.ui.profile.register.RegisterButton
import com.fone.filmone.ui.profile.register.SnsInputComponent
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme

@Composable
fun ActorProfileRegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ActorProfileRegisterViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState.actorProfileRegisterUiEvent) {
        listenUiEvent(
            uiEvent = uiState.actorProfileRegisterUiEvent,
            navController = navController
        )
    }

    Scaffold(
        modifier = modifier
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
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterClick = viewModel::registerProfile
            )

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                PictureComponent(
                    pictureList = uiState.pictureEncodedDataList,
                    onUpdateProfileImage = { encodedString ->
                        viewModel.updateImage(encodedString = encodedString, false)
                    },
                    onRemoveImage = { encodedString ->
                        viewModel.updateImage(encodedString = encodedString, remove = true)
                    }
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
                    onNameChanged = viewModel::updateName,
                    onUpdateBirthday = viewModel::updateBirthday,
                    onUpdateGender = viewModel::updateGender,
                    onUpdateGenderTag = viewModel::updateGenderTag,
                    onUpdateComments = viewModel::updateComments,
                    onUpdateHeight = viewModel::updateHeight,
                    onUpdateWeight = viewModel::updateWeight,
                    onUpdateEmail = viewModel::updateEmail,
                    onUpdateAbility = viewModel::updateAbility,
                    onUpdateSns = viewModel::updateSns
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                DetailInputComponent(
                    detailInfo = uiState.detailInfo,
                    detailInfoTextLimit = uiState.detailInfoTextLimit,
                    onUpdateDetailInfo = viewModel::updateDetailInfo
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                CareerInputComponent(
                    currentCareer = uiState.career,
                    careerDetail = uiState.careerDetail,
                    careerDetailTextLimit = uiState.careerDetailTextLimit,
                    onUpdateCareer = viewModel::updateCareer,
                    onUpdateCareerDetail = viewModel::updateCareerDetail
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                CategorySelectComponent(
                    categoryTagEnable = uiState.categoryTagEnable,
                    currentCategory = uiState.categories.toList(),
                    onCategoryTagClick = viewModel::updateCategoryTag,
                    onUpdateCategory = viewModel::updateCategory
                )

                RegisterButton(
                    buttonEnable = uiState.registerButtonEnable,
                    onRegisterButtonClick = viewModel::registerProfile
                )
            }
        }
    }
}

private fun listenUiEvent(
    uiEvent: ActorProfileRegisterUiEvent,
    navController: NavHostController
) {
    when (uiEvent) {
        ActorProfileRegisterUiEvent.Clear -> Unit
        ActorProfileRegisterUiEvent.RegisterComplete -> {
            navController.popBackStack()
        }
    }
}

@Composable
private fun TitleComponent(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit
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
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        NameInputComponent(name = name, onNameChanged = onNameChanged)

        Spacer(modifier = Modifier.height(20.dp))

        HookingCommentsComponent(
            hookingComments = hookingComments,
            commentsTextLimit = commentsTextLimit,
            onUpdateComments = onUpdateComments
        )

        Spacer(modifier = Modifier.height(20.dp))

        BirthdayInputComponent(
            birthday = birthday,
            genderTagEnable = genderTagEnable,
            currentGender = currentGender,
            onUpdateBirthday = onUpdateBirthday,
            onUpdateGender = onUpdateGender,
            onUpdateGenderTag = onUpdateGenderTag,
        )

        Spacer(modifier = Modifier.height(20.dp))

        HeightWeightInputComponent(
            height = height,
            weight = weight,
            onUpdateHeight = onUpdateHeight,
            onUpdateWeight = onUpdateWeight
        )

        Spacer(modifier = Modifier.height(20.dp))

        EmailInputComponent(email = email, onUpdateEmail = onUpdateEmail)

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
            modifier = Modifier.weight(1f),
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
            modifier = Modifier.weight(1f),
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
