package com.fone.filmone.ui.recruiting.common.staff

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingDialogState
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingFocusEvent
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingUiModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingViewModelState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class StaffRecruitingViewModel : BaseViewModel() {
    abstract val viewModelState: MutableStateFlow<StaffRecruitingViewModelState>

    abstract val uiState: StateFlow<StaffRecruitingUiModel>

    private val _dialogState =
        MutableStateFlow<StaffRecruitingDialogState>(StaffRecruitingDialogState.Clear)
    val dialogState: StateFlow<StaffRecruitingDialogState> = _dialogState.asStateFlow()

    fun registerJobOpening() {
        val isValidationPassed = checkValidation()

        if (isValidationPassed) {
            register()
        } else {
            showToast(R.string.toast_recruiting_register_fail)
            return
        }
    }

    abstract fun register(): Job

    private fun checkValidation(): Boolean {
        if (isInvalidateStep1()) {
            return false
        }

        if (isInvalidateStep2()) {
            return false
        }

        if (isInvalidateStep4()) {
            return false
        }

        if (isInvalidateStep5()) {
            return false
        }

        return true
    }

    private fun isInvalidateStep1(): Boolean {
        val step1UiModel = uiState.value.staffRecruitingStep1UiModel

        if (step1UiModel.titleText.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Title)
            return true
        }

        if (step1UiModel.categories.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Category)
            return true
        }

        if (step1UiModel.deadlineTagEnable.not() && step1UiModel.deadlineDate.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Deadline)
            return true
        }

        if (step1UiModel.deadlineTagEnable.not() && PatternUtil.isValidDate(step1UiModel.deadlineDate)
        ) {
            updateFocusEvent(StaffRecruitingFocusEvent.Deadline)
            return true
        }

        if (step1UiModel.recruitmentDomains.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Domain)
            return true
        }

        if (step1UiModel.recruitmentNumber.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.RecruitmentNumber)
            return true
        }

        return false
    }

    private fun isInvalidateStep2(): Boolean {
        val step2UiModel = uiState.value.staffRecruitingStep2UiModel

        if (step2UiModel.production.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Production)
            return true
        }

        if (step2UiModel.workTitle.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.WorkTitle)
            return true
        }

        if (step2UiModel.directorName.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.DirectorName)
            return true
        }

        if (step2UiModel.genre.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Genre)
            return true
        }

        return false
    }

    private fun isInvalidateStep4(): Boolean {
        val step4UiModel = uiState.value.staffRecruitingStep4UiModel

        if (step4UiModel.detailInfo.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Detail)
            return true
        }

        return false
    }

    private fun isInvalidateStep5(): Boolean {
        val step5UiModel = uiState.value.staffRecruitingStep5UiModel

        if (step5UiModel.manager.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Manager)
            return true
        }

        if (step5UiModel.email.isEmpty()) {
            updateFocusEvent(StaffRecruitingFocusEvent.Email)
            return true
        }

        return false
    }

    fun updateDialogState(dialogState: StaffRecruitingDialogState) {
        _dialogState.value = dialogState
    }

    private fun updateFocusEvent(staffRecruitingFocusEvent: StaffRecruitingFocusEvent) {
        viewModelState.update { state ->
            state.copy(focusEvent = staffRecruitingFocusEvent)
        }

        viewModelScope.launch {
            delay(1000)
            viewModelState.update { state ->
                state.copy(focusEvent = null)
            }
        }
    }

    fun updateTitle(title: String) {
        viewModelState.update {
            it.copy(
                staffRecruitingStep1UiModel = it.staffRecruitingStep1UiModel.copy(titleText = title),
            )
        }

        updateRegisterButtonState()
    }

    fun updateCategory(category: Category, enable: Boolean) {
        viewModelState.update { state ->
            var updatedCategories = state.staffRecruitingStep1UiModel.categories

            if (enable) {
                if (updatedCategories.size < 2) {
                    updatedCategories = updatedCategories + setOf(category)
                }
            } else {
                updatedCategories = updatedCategories.filterNot { it == category }.toSet()
            }

            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    categories = updatedCategories
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateDeadlineDate(deadlineDate: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    deadlineDate = deadlineDate,
                    deadlineTagEnable = if (deadlineDate.isNotEmpty()) {
                        false
                    } else {
                        state.staffRecruitingStep1UiModel.deadlineTagEnable
                    },
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateDeadlineTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    deadlineTagEnable = enable,
                    deadlineDate = if (enable) {
                        ""
                    } else {
                        state.staffRecruitingStep1UiModel.deadlineDate
                    },
                ),
            )
        }
    }

    fun updateRecruitmentDomains(domains: Set<Domain>) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    recruitmentDomains = domains,
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateRecruitmentNumber(number: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    recruitmentNumber = number,
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateRecruitmentGender(gender: Gender, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    recruitmentGender = if (enable) {
                        gender
                    } else {
                        null
                    },
                    genderTagEnable = enable.not(),
                ),
            )
        }
    }

    fun updateGenderTag() {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    genderTagEnable = true,
                    recruitmentGender = null,
                ),
            )
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    ageRange = ageRange,
                    ageTagEnable = ageRange == state.staffRecruitingStep1UiModel.defaultAgeRange,
                ),
            )
        }
    }

    fun updateAgeTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    ageTagEnable = enable,
                    ageRange = if (enable) {
                        state.staffRecruitingStep1UiModel.defaultAgeRange
                    } else {
                        state.staffRecruitingStep1UiModel.ageRange
                    },
                ),
            )
        }
    }

    fun updateCareer(career: Career, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    career = if (enable) {
                        career
                    } else {
                        null
                    },
                    careerTagEnable = enable.not(),
                ),
            )
        }
    }

    fun updateCareerTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    careerTagEnable = enable,
                    career = if (enable) {
                        null
                    } else {
                        state.staffRecruitingStep1UiModel.career
                    },
                ),
            )
        }
    }

    fun updateProduction(production: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    production = production,
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateWorkTitle(workTitle: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    workTitle = workTitle,
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateDirectorName(directorName: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    directorName = directorName,
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateGenre(genre: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    genre = genre,
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateLogLine(logLine: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    logLine = logLine,
                ),
            )
        }
    }

    fun updateLogLinePrivate() {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    isLogLinePrivate = state.staffRecruitingStep2UiModel.isLogLinePrivate.not(),
                ),
            )
        }
    }

    fun updateLocation(location: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    location = location,
                    locationTagEnable = if (location.isNotEmpty()) {
                        false
                    } else {
                        state.staffRecruitingStep3UiModel.locationTagEnable
                    },
                ),
            )
        }
    }

    fun updateLocationTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    locationTagEnable = enable,
                    location = if (enable) {
                        ""
                    } else {
                        state.staffRecruitingStep3UiModel.location
                    },
                ),
            )
        }
    }

    fun updatePeriod(period: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    period = period,
                    periodTagEnable = if (period.isNotEmpty()) {
                        false
                    } else {
                        state.staffRecruitingStep3UiModel.periodTagEnable
                    },
                ),
            )
        }
    }

    fun updatePeriodTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    periodTagEnable = enable,
                    period = if (enable) {
                        ""
                    } else {
                        state.staffRecruitingStep3UiModel.period
                    },
                ),
            )
        }
    }

    fun updatePay(pay: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    pay = pay,
                    payTagEnable = if (pay.isNotEmpty()) {
                        false
                    } else {
                        state.staffRecruitingStep3UiModel.payTagEnable
                    },
                ),
            )
        }
    }

    fun updatePayTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    payTagEnable = enable,
                    pay = if (enable) {
                        ""
                    } else {
                        state.staffRecruitingStep3UiModel.pay
                    },
                ),
            )
        }
    }

    fun updateDetailInfo(detailInfo: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep4UiModel = state.staffRecruitingStep4UiModel.copy(
                    detailInfo = detailInfo,
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateManager(manager: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep5UiModel = state.staffRecruitingStep5UiModel.copy(
                    manager = manager,
                ),
            )
        }

        updateRegisterButtonState()
    }

    fun updateEmail(email: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep5UiModel = state.staffRecruitingStep5UiModel.copy(
                    email = email,
                ),
            )
        }

        updateRegisterButtonState()
    }

    private fun updateRegisterButtonState() {
        val modelState = viewModelState.value

        val enable =
            modelState.staffRecruitingStep1UiModel.titleText.isNotEmpty() && modelState.staffRecruitingStep1UiModel.categories.isNotEmpty() &&
                PatternUtil.isValidDate(modelState.staffRecruitingStep1UiModel.deadlineDate) &&
                modelState.staffRecruitingStep1UiModel.recruitmentDomains.isNotEmpty() && modelState.staffRecruitingStep1UiModel.recruitmentNumber.isNotEmpty() &&
                modelState.staffRecruitingStep2UiModel.production.isNotEmpty() && modelState.staffRecruitingStep2UiModel.workTitle.isNotEmpty() &&
                modelState.staffRecruitingStep2UiModel.directorName.isNotEmpty() && modelState.staffRecruitingStep2UiModel.genre.isNotEmpty() &&
                modelState.staffRecruitingStep4UiModel.detailInfo.isNotEmpty() && modelState.staffRecruitingStep5UiModel.manager.isNotEmpty() &&
                PatternUtil.isValidEmail(modelState.staffRecruitingStep5UiModel.email)

        viewModelState.update { state ->
            state.copy(registerButtonEnable = enable)
        }
    }
}
