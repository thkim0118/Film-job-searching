package com.fone.filmone.ui.profile.common.staff

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.main.job.common.DomainInputComponent
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
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileDialogState
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileUiEvent
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileUiModel
import com.fone.filmone.ui.recruiting.common.staff.DomainSelectDialog
import com.fone.filmone.ui.theme.FColor

@Composable
fun StaffProfileScreen(
    modifier: Modifier,
    navController: NavHostController,
    uiState: StaffProfileUiModel,
    dialogState: StaffProfileDialogState,
    baseViewModel: BaseViewModel,
    onRegisterClick: () -> Unit,
    onUpdateProfileImage: (String, Boolean) -> Unit,
    onRemoveImage: (String, Boolean) -> Unit,
    onNameChanged: (String) -> Unit,
    onUpdateBirthday: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderTag: (Boolean) -> Unit,
    onUpdateComments: (String) -> Unit,
    onUpdateDomains: () -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdateSpecialty: (String) -> Unit,
    onUpdateSns: (String) -> Unit,
    onUpdateDetailInfo: (String) -> Unit,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerDetail: (String) -> Unit,
    onCategoryTagClick: (Boolean) -> Unit,
    onUpdateCategory: (Category, Boolean) -> Unit,
    onDialogDismiss: (List<Domain>) -> Unit,
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = uiState.staffProfileUiEvent) {
        listenUiEvent(
            uiEvent = uiState.staffProfileUiEvent,
            navController = navController
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .defaultSystemBarPadding()
            .toastPadding()
    ) {
        Scaffold(
            modifier = Modifier,
            snackbarHost = {
                FToast(baseViewModel = baseViewModel, hostState = it)
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
                    onRegisterClick = onRegisterClick
                )

                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    PictureComponent(
                        pictureList = uiState.pictureEncodedDataList,
                        onUpdateProfileImage = { encodedString ->
                            onUpdateProfileImage(encodedString, false)
                        },
                        onRemoveImage = { encodedString ->
                            onRemoveImage(encodedString, true)
                        },
                        limit = 9,
                    )

                    Divider(thickness = 8.dp, color = FColor.Divider2)

                    UserInfoInputComponent(
                        name = uiState.name,
                        birthday = uiState.birthday,
                        genderTagEnable = uiState.genderTagEnable,
                        currentGender = uiState.gender,
                        hookingComments = uiState.hookingComments,
                        commentsTextLimit = uiState.commentsTextLimit,
                        selectedDomains = uiState.domains.toList(),
                        email = uiState.email,
                        ability = uiState.specialty,
                        sns = uiState.sns,
                        onNameChanged = onNameChanged,
                        onUpdateBirthday = onUpdateBirthday,
                        onUpdateGender = onUpdateGender,
                        onUpdateGenderTag = onUpdateGenderTag,
                        onUpdateComments = onUpdateComments,
                        onUpdateDomains = onUpdateDomains,
                        onUpdateEmail = onUpdateEmail,
                        onUpdateAbility = onUpdateSpecialty,
                        onUpdateSns = onUpdateSns
                    )

                    Divider(thickness = 8.dp, color = FColor.Divider2)

                    DetailInputComponent(
                        detailInfo = uiState.detailInfo,
                        detailInfoTextLimit = uiState.detailInfoTextLimit,
                        onUpdateDetailInfo = onUpdateDetailInfo
                    )

                    Divider(thickness = 8.dp, color = FColor.Divider2)

                    CareerInputComponent(
                        currentCareer = uiState.career,
                        careerDetail = uiState.careerDetail,
                        careerDetailTextLimit = uiState.careerDetailTextLimit,
                        onUpdateCareer = onUpdateCareer,
                        onUpdateCareerDetail = onUpdateCareerDetail
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
                        onRegisterButtonClick = onRegisterClick
                    )
                }
            }
        }

        DialogScreen(
            dialogState = dialogState,
            onDismiss = onDialogDismiss,
        )
    }
}

private fun listenUiEvent(
    uiEvent: StaffProfileUiEvent,
    navController: NavHostController,
) {
    when (uiEvent) {
        StaffProfileUiEvent.Clear -> Unit
        StaffProfileUiEvent.RegisterComplete -> {
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
        titleText = stringResource(id = R.string.profile_register_staff_title_text),
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
    selectedDomains: List<Domain>,
    email: String,
    ability: String,
    sns: String,
    onNameChanged: (String) -> Unit,
    onUpdateBirthday: (String) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderTag: (Boolean) -> Unit,
    onUpdateComments: (String) -> Unit,
    onUpdateDomains: () -> Unit,
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
    }

    DomainInputComponent(
        selectedDomains = selectedDomains,
        onUpdateDomains = onUpdateDomains
    )

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
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
private fun DialogScreen(
    dialogState: StaffProfileDialogState,
    onDismiss: (List<Domain>) -> Unit,
) {
    when (dialogState) {
        StaffProfileDialogState.Clear -> Unit
        is StaffProfileDialogState.DomainSelectDialog -> {
            DomainSelectDialog(
                selectedDomains = dialogState.selectedDomains,
                onDismissRequest = { domains ->
                    onDismiss(domains)
                }
            )
        }
    }
}
