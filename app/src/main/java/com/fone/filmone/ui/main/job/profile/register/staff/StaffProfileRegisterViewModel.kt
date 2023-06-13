package com.fone.filmone.ui.main.job.profile.register.staff

import androidx.lifecycle.viewModelScope
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StaffProfileRegisterViewModel @Inject constructor(
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(StaffProfileRegisterViewModelState())

    val uiState = viewModelState
        .map(StaffProfileRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private val _dialogState =
        MutableStateFlow<StaffProfileRegisterDialogState>(StaffProfileRegisterDialogState.Clear)
    val dialogState: StateFlow<StaffProfileRegisterDialogState> = _dialogState.asStateFlow()

    fun register() {
        val state = viewModelState.value

        if (state.registerButtonEnable.not()) {
            return
        }


    }

    fun updateDialogState(dialogState: StaffProfileRegisterDialogState) {
        _dialogState.value = dialogState
    }

    fun updateName(name: String) {
        viewModelState.update { state ->
            state.copy(name = name)
        }

        updateRegisterButtonState()
    }

    fun updateBirthday(birthday: String) {
        viewModelState.update { state ->
            state.copy(birthday = birthday)
        }

        updateRegisterButtonState()
    }

    fun updateGender(gender: Gender) {
        viewModelState.update { state ->
            state.copy(gender = gender)
        }
    }

    fun updateGenderTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                gender = if (enable) {
                    Gender.IRRELEVANT
                } else {
                    null
                }
            )
        }
    }

    fun updateComments(comments: String) {
        viewModelState.update { state ->
            state.copy(hookingComments = comments)
        }

        updateRegisterButtonState()
    }

    fun updateRecruitmentDomains(domains: Set<Domain>) {
        viewModelState.update { state ->
            state.copy(
                domains = domains
            )
        }

        updateRegisterButtonState()
    }

    fun updateEmail(email: String) {
        viewModelState.update { state ->
            state.copy(email = email)
        }

        updateRegisterButtonState()
    }

    fun updateAbility(ability: String) {
        viewModelState.update { state ->
            state.copy(ability = ability)
        }
    }

    fun updateSns(sns: String) {
        viewModelState.update { state ->
            state.copy(sns = sns)
        }
    }

    fun updateDetailInfo(detailInfo: String) {
        viewModelState.update { state ->
            state.copy(detailInfo = detailInfo)
        }

        updateRegisterButtonState()
    }

    fun updateCareer(career: Career, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                career = if (enable) {
                    career
                } else {
                    null
                }
            )
        }
    }

    fun updateCareerDetail(careerDetail: String) {
        viewModelState.update { state ->
            state.copy(careerDetail = careerDetail)
        }
    }

    fun updateCategoryTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                categoryTagEnable = enable,
                categories = if (enable) {
                    Category.values().toSet()
                } else {
                    emptySet()
                }
            )
        }
    }

    fun updateCategory(category: Category, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                categories = if (enable) {
                    state.categories + setOf(category)
                } else {
                    state.categories.filterNot { it == category }.toSet()
                }
            )
        }
    }

    fun updateImage(encodedString: String, remove: Boolean) {
        viewModelState.update { state ->
            state.copy(
                pictureList = if (remove) {
                    val copyList = state.pictureList.toMutableList().apply {
                        remove(encodedString)
                    }

                    copyList
                } else {
                    state.pictureList + encodedString
                }
            )
        }
    }

    private fun updateRegisterButtonState() {
        val modelState = viewModelState.value

        val isEnable = modelState.name.isNotEmpty() && modelState.hookingComments.isNotEmpty() &&
                modelState.birthday.isNotEmpty() && PatternUtil.isValidDate(modelState.birthday) && modelState.domains.isNotEmpty() &&
                modelState.email.isNotEmpty() && PatternUtil.isValidEmail(modelState.email) && modelState.detailInfo.isNotEmpty()

        viewModelState.update { state ->
            state.copy(registerButtonEnable = isEnable)
        }
    }
}

private data class StaffProfileRegisterViewModelState(
    val pictureList: List<String> = emptyList(),
    val name: String = "",
    val hookingComments: String = "",
    val commentsTextLimit: Int = 50,
    val birthday: String = "",
    val gender: Gender? = Gender.IRRELEVANT,
    val genderTagEnable: Boolean = false,
    val domains: Set<Domain> = emptySet(),
    val email: String = "",
    val ability: String = "",
    val sns: String = "",
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 200,
    val career: Career? = null,
    val careerDetail: String = "",
    val careerDetailTextLimit: Int = 500,
    val categories: Set<Category> = emptySet(),
    val categoryTagEnable: Boolean = false,
    val registerButtonEnable: Boolean = false,
) {
    fun toUiState(): StaffProfileRegisterUiModel = StaffProfileRegisterUiModel(
        pictureList = pictureList,
        name = name,
        hookingComments = hookingComments,
        commentsTextLimit = commentsTextLimit,
        birthday = birthday,
        gender = gender,
        genderTagEnable = genderTagEnable,
        domains = domains,
        email = email,
        ability = ability,
        sns = sns,
        detailInfo = detailInfo,
        detailInfoTextLimit = detailInfoTextLimit,
        career = career,
        careerDetail = careerDetail,
        careerDetailTextLimit = careerDetailTextLimit,
        categories = categories,
        categoryTagEnable = categoryTagEnable,
        registerButtonEnable = registerButtonEnable
    )
}

data class StaffProfileRegisterUiModel(
    val pictureList: List<String>,
    val name: String,
    val hookingComments: String,
    val commentsTextLimit: Int,
    val birthday: String,
    val gender: Gender?,
    val genderTagEnable: Boolean,
    val domains: Set<Domain>,
    val email: String,
    val ability: String,
    val sns: String,
    val detailInfo: String,
    val detailInfoTextLimit: Int,
    val career: Career?,
    val careerDetail: String,
    val careerDetailTextLimit: Int,
    val categories: Set<Category>,
    val categoryTagEnable: Boolean,
    val registerButtonEnable: Boolean,
)

sealed class StaffProfileRegisterDialogState {
    object Clear : StaffProfileRegisterDialogState()
    data class DomainSelectDialog(
        val selectedDomains: List<Domain>
    ) : StaffProfileRegisterDialogState()
}