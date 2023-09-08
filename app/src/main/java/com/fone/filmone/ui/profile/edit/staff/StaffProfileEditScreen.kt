package com.fone.filmone.ui.profile.edit.staff

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.ui.profile.common.staff.StaffProfileScreen
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileDialogState

@Composable
fun StaffProfileEditScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: StaffProfileEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    StaffProfileScreen(
        modifier = modifier,
        navController = navController,
        uiState = uiState,
        dialogState = dialogState,
        baseViewModel = viewModel,
        onRegisterClick = viewModel::registerProfile,
        onUpdateProfileImage = viewModel::updateImage,
        onRemoveImage = viewModel::updateImage,
        onNameChanged = viewModel::updateName,
        onUpdateBirthday = viewModel::updateBirthday,
        onUpdateGender = viewModel::updateGender,
        onUpdateGenderTag = viewModel::updateGenderTag,
        onUpdateComments = viewModel::updateComments,
        onUpdateDomains = {
            viewModel.updateDialogState(
                StaffProfileDialogState.DomainSelectDialog(
                    selectedDomains = uiState.domains.toList()
                )
            )
        },
        onUpdateEmail = viewModel::updateEmail,
        onUpdateSpecialty = viewModel::updateSpecialty,
        onUpdateSns = viewModel::updateSns,
        onUpdateDetailInfo = viewModel::updateDetailInfo,
        onUpdateCareer = viewModel::updateCareer,
        onUpdateCareerDetail = viewModel::updateCareerDetail,
        onCategoryTagClick = viewModel::updateCategoryTag,
        onUpdateCategory = viewModel::updateCategory,
        onDialogDismiss = { domains ->
            viewModel.updateRecruitmentDomains(domains.toSet())
            viewModel.updateDialogState(StaffProfileDialogState.Clear)
        }
    )
}
