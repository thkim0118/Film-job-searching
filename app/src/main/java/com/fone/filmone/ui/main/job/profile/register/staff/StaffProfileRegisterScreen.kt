package com.fone.filmone.ui.main.job.profile.register.staff

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.main.job.common.DomainInputComponent
import com.fone.filmone.ui.main.job.profile.register.AbilityInputComponent
import com.fone.filmone.ui.main.job.profile.register.BirthdayInputComponent
import com.fone.filmone.ui.main.job.profile.register.CareerInputComponent
import com.fone.filmone.ui.main.job.profile.register.CategorySelectComponent
import com.fone.filmone.ui.main.job.profile.register.DetailInputComponent
import com.fone.filmone.ui.main.job.profile.register.EmailInputComponent
import com.fone.filmone.ui.main.job.profile.register.HookingCommentsComponent
import com.fone.filmone.ui.main.job.profile.register.NameInputComponent
import com.fone.filmone.ui.main.job.profile.register.PictureComponent
import com.fone.filmone.ui.main.job.profile.register.RegisterButton
import com.fone.filmone.ui.main.job.profile.register.SnsInputComponent
import com.fone.filmone.ui.main.job.recruiting.register.staff.DomainSelectDialog
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme

@Composable
fun StaffProfileRegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: StaffProfileRegisterViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .defaultSystemBarPadding()
            .toastPadding()
    ) {
        Scaffold(
            modifier = Modifier,
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
                    onRegisterClick = {

                    }
                )

                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    PictureComponent(
                        pictureList = uiState.pictureList,
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
                        selectedDomains = uiState.domains.toList(),
                        email = uiState.email,
                        ability = uiState.ability,
                        sns = uiState.sns,
                        onNameChanged = viewModel::updateName,
                        onUpdateBirthday = viewModel::updateBirthday,
                        onUpdateGender = viewModel::updateGender,
                        onUpdateGenderTag = viewModel::updateGenderTag,
                        onUpdateComments = viewModel::updateComments,
                        onUpdateDomains = {
                            viewModel.updateDialogState(
                                StaffProfileRegisterDialogState.DomainSelectDialog(
                                    selectedDomains = uiState.domains.toList()
                                )
                            )
                        },
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
                        onRegisterButtonClick = viewModel::register
                    )
                }
            }
        }

        DialogScreen(
            dialogState = dialogState,
            onDismiss = { domains ->
                viewModel.updateRecruitmentDomains(domains.toSet())
                viewModel.updateDialogState(StaffProfileRegisterDialogState.Clear)
            }
        )
    }
}

@Composable
private fun TitleComponent(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit
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
    onUpdateGender: (Gender) -> Unit,
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
    dialogState: StaffProfileRegisterDialogState,
    onDismiss: (List<Domain>) -> Unit
) {
    when (dialogState) {
        StaffProfileRegisterDialogState.Clear -> Unit
        is StaffProfileRegisterDialogState.DomainSelectDialog -> {
            DomainSelectDialog(
                selectedDomains = dialogState.selectedDomains,
                onDismissRequest = { domains ->
                    onDismiss(domains)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StaffProfileRegisterScreenPreview() {
    FilmOneTheme {
        StaffProfileRegisterScreen()
    }
}