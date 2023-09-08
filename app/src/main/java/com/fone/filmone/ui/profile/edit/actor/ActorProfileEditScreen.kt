package com.fone.filmone.ui.profile.edit.actor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fone.filmone.ui.profile.common.actor.ActorProfileScreen

@Composable
fun ActorProfileEditScreen(
    navController: NavHostController,
    viewModel: ActorProfileEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    ActorProfileScreen(
        navController = navController,
        uiState = uiState,
        baseViewModel = viewModel,
        onRegisterClick = viewModel::registerProfile,
        onUpdateProfileImage = viewModel::updateImage,
        onRemoveImage = viewModel::updateImage,
        onNameChanged = viewModel::updateName,
        onUpdateBirthday = viewModel::updateBirthday,
        onUpdateGender = viewModel::updateGender,
        onUpdateGenderTag = viewModel::updateGenderTag,
        onUpdateComments = viewModel::updateComments,
        onUpdateHeight = viewModel::updateHeight,
        onUpdateWeight = viewModel::updateWeight,
        onUpdateEmail = viewModel::updateEmail,
        onUpdateAbility = viewModel::updateAbility,
        onUpdateSns = viewModel::updateSns,
        onUpdateDetailInfo = viewModel::updateDetailInfo,
        onUpdateCareer = viewModel::updateCareer,
        onUpdateCareerDetail = viewModel::updateCareerDetail,
        onCategoryTagClick = viewModel::updateCategoryTag,
        onUpdateCategory = viewModel::updateCategory,
        onRegisterButtonClick = viewModel::registerProfile,
    )
}
