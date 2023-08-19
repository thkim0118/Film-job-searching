package com.fone.filmone.ui.recruiting.edit.actor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fone.filmone.ui.recruiting.common.actor.ActorRecruitingScreen

@Composable
fun ActorRecruitingEditScreen(
    navController: NavHostController,
    viewModel: ActorRecruitingEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    ActorRecruitingScreen(
        navController = navController,
        uiState = uiState,
        baseViewModel = viewModel,
        onRegisterClick = viewModel::registerJobOpening,
        onUpdateTitleText = viewModel::updateTitle,
        onUpdateCategories = viewModel::updateCategory,
        onUpdateDeadlineDate = viewModel::updateDeadlineDate,
        onUpdateRecruitmentActor = viewModel::updateRecruitmentActor,
        onUpdateRecruitmentNumber = viewModel::updateRecruitmentNumber,
        onUpdateGender = viewModel::updateRecruitmentGender,
        onUpdateAgeReset = viewModel::updateAgeRangeReset,
        onUpdateAgeRange = viewModel::updateAgeRange,
        onUpdateCareer = viewModel::updateCareer,
        onUpdateDeadlineTag = viewModel::updateDeadlineTag,
        onUpdateGenderTag = viewModel::updateGenderTag,
        onUpdateCareerTag = viewModel::updateCareerTag,
        onUpdateProduction = viewModel::updateProduction,
        onUpdateWorkTitle = viewModel::updateWorkTitle,
        onUpdateDirectorName = viewModel::updateDirectorName,
        onUpdateGenre = viewModel::updateGenre,
        onUpdateLogLine = viewModel::updateLogLine,
        onUpdateLogLineTag = viewModel::updateLogLineTag,
        onUpdateLocationTag = viewModel::updateLocationTagEnable,
        onUpdatePeriodTag = viewModel::updatePeriodTagEnable,
        onUpdatePayTag = viewModel::updatePayTagEnable,
        onUpdateLocation = viewModel::updateLocation,
        onUpdatePeriod = viewModel::updatePeriod,
        onUpdatePay = viewModel::updatePay,
        onUpdateDetailInfo = viewModel::updateDetailInfo,
        onUpdateManager = viewModel::updateManager,
        onUpdateEmail = viewModel::updateEmail,
    )
}
