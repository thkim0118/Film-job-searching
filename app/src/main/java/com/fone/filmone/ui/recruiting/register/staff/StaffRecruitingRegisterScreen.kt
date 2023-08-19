package com.fone.filmone.ui.recruiting.register.staff

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fone.filmone.ui.recruiting.common.staff.StaffRecruitingScreen
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingDialogState

@Composable
fun StaffRecruitingRegisterScreen(
    navController: NavHostController,
    viewModel: StaffRecruitingRegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    StaffRecruitingScreen(
        navController = navController,
        uiState = uiState,
        dialogState = dialogState,
        baseViewModel = viewModel,
        onRegisterClick = viewModel::registerJobOpening,
        onUpdateTitleText = viewModel::updateTitle,
        onUpdateCategories = viewModel::updateCategory,
        onUpdateDeadlineDate = viewModel::updateDeadlineDate,
        onUpdateDeadlineTag = viewModel::updateDeadlineTag,
        onUpdateDomains = {
            viewModel.updateDialogState(
                StaffRecruitingDialogState.DomainSelectDialog(
                    selectedDomains = uiState.staffRecruitingStep1UiModel.recruitmentDomains.toList(),
                ),
            )
        },
        onUpdateRecruitmentNumber = viewModel::updateRecruitmentNumber,
        onUpdateGender = viewModel::updateRecruitmentGender,
        onUpdateGenderTag = viewModel::updateGenderTag,
        onUpdateAgeRange = viewModel::updateAgeRange,
        onUpdateAgeTag = viewModel::updateAgeTag,
        onUpdateCareer = viewModel::updateCareer,
        onUpdateCareerTag = viewModel::updateCareerTag,
        onUpdateProduction = viewModel::updateProduction,
        onUpdateWorkTitle = viewModel::updateWorkTitle,
        onUpdateDirectorName = viewModel::updateDirectorName,
        onUpdateGenre = viewModel::updateGenre,
        onUpdateLogLine = viewModel::updateLogLine,
        onLogLineTagClick = viewModel::updateLogLinePrivate,
        onUpdateLocationTag = viewModel::updateLocationTag,
        onUpdatePeriodTag = viewModel::updatePeriodTag,
        onUpdatePayTag = viewModel::updatePayTag,
        onUpdateLocation = viewModel::updateLocation,
        onUpdatePeriod = viewModel::updatePeriod,
        onUpdatePay = viewModel::updatePay,
        onUpdateDetailInfo = viewModel::updateDetailInfo,
        onUpdateManager = viewModel::updateManager,
        onUpdateEmail = viewModel::updateEmail,
        onDismiss = { domains ->
            viewModel.updateRecruitmentDomains(domains.toSet())
            viewModel.updateDialogState(StaffRecruitingDialogState.Clear)
        },
    )
}
