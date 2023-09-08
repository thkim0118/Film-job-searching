package com.fone.filmone.ui.profile.common.actor

import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileUiModel
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileViewModelState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class ActorProfileViewModel : BaseViewModel() {
    abstract val viewModelState: MutableStateFlow<ActorProfileViewModelState>
    abstract val uiState: StateFlow<ActorProfileUiModel>

    abstract fun register(imageUrls: List<String>): Job

    abstract fun uploadProfileImages(): Job

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

        if (state.pictureList.isEmpty()) {
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

        if (state.height.isEmpty()) {
            return false
        }

        if (state.weight.isEmpty()) {
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
                gender = gender,
                genderTagEnable = if (enable) {
                    false
                } else {
                    state.genderTagEnable
                }
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

    fun updateHeight(height: String) {
        viewModelState.update { state ->
            state.copy(height = height)
        }

        updateRegisterButtonState()
    }

    fun updateWeight(weight: String) {
        viewModelState.update { state ->
            state.copy(weight = weight)
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
            state.copy(specialty = ability)
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
            modelState.birthday.isNotEmpty() && PatternUtil.isValidDate(modelState.birthday) &&
            modelState.height.isNotEmpty() && modelState.weight.isNotEmpty() && modelState.email.isNotEmpty() &&
            PatternUtil.isValidEmail(modelState.email) && modelState.detailInfo.isNotEmpty()

        viewModelState.update { state ->
            state.copy(registerButtonEnable = isEnable)
        }
    }
}
