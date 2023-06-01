package com.fone.filmone.ui.main.job.profile.register.actor

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ActorProfileRegisterViewModel @Inject constructor(
) : BaseViewModel() {

    private val viewModelState = MutableStateFlow(ActorProfileRegisterViewModelState())

    val uiState = viewModelState
        .map(ActorProfileRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )
}

private data class ActorProfileRegisterViewModelState(
    val pictureUriList: List<Uri> = emptyList(),
    val name: String = "",
    val hookingComments: String = "",
    val birthday: String = "",
    val gender: Gender = Gender.IRRELEVANT,
    val height: String = "",
    val weight: String = "",
    val email: String = "",
    val ability: String = "",
    val sns: String = "",
    val detail: String = "",
    val career: Career? = null,
    val careerDetail: String = "",
    val categories: List<Category> = emptyList()
) {
    fun toUiState(): ActorProfileRegisterUiModel = ActorProfileRegisterUiModel(
        pictureUriList = pictureUriList,
        name = name,
        hookingComments = hookingComments,
        birthday = birthday,
        gender = gender,
        height = height,
        weight = weight,
        email = email,
        ability = ability,
        sns = sns,
        detail = detail,
        career = career,
        careerDetail = careerDetail,
        categories = categories
    )
}

data class ActorProfileRegisterUiModel(
    val pictureUriList: List<Uri>,
    val name: String,
    val hookingComments: String,
    val birthday: String,
    val gender: Gender,
    val height: String,
    val weight: String,
    val email: String,
    val ability: String,
    val sns: String,
    val detail: String,
    val career: Career?,
    val careerDetail: String,
    val categories: List<Category>
)