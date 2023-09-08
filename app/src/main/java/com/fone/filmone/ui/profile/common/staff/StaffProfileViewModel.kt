package com.fone.filmone.ui.profile.common.staff

import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileDialogState
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileUiModel
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileViewModelState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class StaffProfileViewModel : BaseViewModel() {
    abstract val viewModelState: MutableStateFlow<StaffProfileViewModelState>
    abstract val uiState: StateFlow<StaffProfileUiModel>
    private val _dialogState =
        MutableStateFlow<StaffProfileDialogState>(StaffProfileDialogState.Clear)
    val dialogState: StateFlow<StaffProfileDialogState> = _dialogState.asStateFlow()

    abstract fun register(imageUrls: List<String>): Job

    abstract fun uploadProfileImages(): Job

    fun updateDialogState(dialogState: StaffProfileDialogState) {
        _dialogState.value = dialogState
    }

    fun registerProfile() {
        val state = viewModelState.value

        if (state.registerButtonEnable.not()) {
            return
        }

        val isValidationPassed = checkValidation()

        if (isValidationPassed) {
            uploadProfileImages()
        } else {
            showToast(R.string.toast_recruiting_register_fail)
            return
        }
    }

    private fun checkValidation(): Boolean {
        val state = uiState.value

        if (state.pictureEncodedDataList.isEmpty()) {
            return false
        }

        if (state.name.isEmpty()) {
            return false
        }

        if (state.hookingComments.isEmpty()) {
            return false
        }

        if (state.birthday.isEmpty()) {
            return false
        }

        if (state.domains.isEmpty()) {
            return false
        }

        if (state.email.isEmpty()) {
            return false
        }

        if (state.detailInfo.isEmpty()) {
            return false
        }

        return true
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

    fun updateGender(gender: Gender, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                gender = if (enable) {
                    gender
                } else {
                    null
                },
                genderTagEnable = enable.not()
            )
        }
    }

    fun updateGenderTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                genderTagEnable = enable,
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

    fun updateSpecialty(specialty: String) {
        viewModelState.update { state ->
            state.copy(specialty = specialty)
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
                },
                categoryTagEnable = if (enable.not()) {
                    false
                } else if (enable && state.categories.size + 1 == Category.values().size) {
                    true
                } else {
                    state.categoryTagEnable
                }
            )
        }
    }

    fun updateImage(encodedString: String, remove: Boolean) {
        viewModelState.update { state ->
            state.copy(
                pictureEncodedDataList = if (remove) {
                    val copyList = state.pictureEncodedDataList.toMutableList().apply {
                        remove(encodedString)
                    }

                    copyList
                } else if (state.pictureEncodedDataList.size < 9) {
                    state.pictureEncodedDataList + encodedString
                } else {
                    state.pictureEncodedDataList
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
