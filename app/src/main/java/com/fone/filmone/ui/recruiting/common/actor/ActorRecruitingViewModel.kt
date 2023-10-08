package com.fone.filmone.ui.recruiting.common.actor

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingFocusEvent
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingUiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingViewModelState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ActorRecruitingViewModel : BaseViewModel() {
    abstract val viewModelState: MutableStateFlow<ActorRecruitingViewModelState>
    abstract val uiState: StateFlow<ActorRecruitingUiModel>

    abstract fun register(): Job

    fun registerJobOpening() {
        val isValidationPassed = checkValidation()

        if (isValidationPassed) {
            register()
        } else {
            showToast(R.string.toast_recruiting_register_fail)
            return
        }
    }

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
        val step1UiModel = uiState.value.actorRecruitingStep1UiModel

        if (step1UiModel.titleText.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.Title)
            return true
        }

        if (step1UiModel.categories.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.Category)
            return true
        }

        if (step1UiModel.deadlineTagEnable.not() && step1UiModel.deadlineDate.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.Deadline)
            return true
        }

        if (step1UiModel.deadlineTagEnable.not() &&
            (!PatternUtil.isValidDate(step1UiModel.deadlineDate) || step1UiModel.deadlineDate == "상시모집")
        ) {
            updateFocusEvent(ActorRecruitingFocusEvent.Deadline)
            return true
        }

        if (step1UiModel.recruitmentNumber.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.RecruitmentNumber)
            return true
        }

        return false
    }

    private fun isInvalidateStep2(): Boolean {
        val step2UiModel = uiState.value.actorRecruitingStep2UiModel

        if (step2UiModel.production.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.Production)
            return true
        }

        if (step2UiModel.workTitle.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.WorkTitle)
            return true
        }

        if (step2UiModel.directorName.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.DirectorName)
            return true
        }

        if (step2UiModel.genre.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.Genre)
            return true
        }

        return false
    }

    private fun isInvalidateStep4(): Boolean {
        val step4UiModel = uiState.value.actorRecruitingStep4UiModel

        if (step4UiModel.detailInfo.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.Detail)
            return true
        }

        return false
    }

    private fun isInvalidateStep5(): Boolean {
        val step5UiModel = uiState.value.actorRecruitingStep5UiModel

        if (step5UiModel.manager.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.Manager)
            return true
        }

        if (step5UiModel.email.isEmpty()) {
            updateFocusEvent(ActorRecruitingFocusEvent.Email)
            return true
        }

        return false
    }

    private fun updateFocusEvent(actorRecruitingFocusEvent: ActorRecruitingFocusEvent) {
        viewModelState.update { state ->
            state.copy(focusEvent = actorRecruitingFocusEvent)
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
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(titleText = title),
            )
        }
    }

    fun updateCategory(category: Category, enable: Boolean) {
        viewModelState.update { state ->
            val currentCategories = state.actorRecruitingStep1UiModel.categories

            if (enable) {
                val newCategories = if (currentCategories.size < 2) {
                    currentCategories + setOf(category)
                } else {
                    currentCategories
                }

                state.copy(
                    actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                        categories = newCategories,
                    ),
                )
            } else {
                val updatedCategories = currentCategories.filterNot { it == category }
                if (currentCategories.size == 2 && updatedCategories.size == 1) {
                    val firstCategory = currentCategories.first()
                    updatedCategories + firstCategory
                }

                state.copy(
                    actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                        categories = updatedCategories,
                    ),
                )
            }
        }
    }

    fun updateDeadlineDate(deadlineDate: String) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    deadlineDate = deadlineDate,
                    deadlineTagEnable = if (it.actorRecruitingStep1UiModel.deadlineTagEnable && deadlineDate.isNotEmpty()) {
                        false
                    } else {
                        it.actorRecruitingStep1UiModel.deadlineTagEnable
                    },
                ),
            )
        }
    }

    fun updateDeadlineTag(enable: Boolean) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    deadlineTagEnable = enable,
                    deadlineDate = if (enable) { "상시모집" } else { "" },
                ),
            )
        }
    }

    fun updateRecruitmentActor(actor: String) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(recruitmentActor = actor),
            )
        }
    }

    fun updateRecruitmentNumber(number: String) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(recruitmentNumber = number),
            )
        }
    }

    fun updateRecruitmentGender(gender: Gender, enable: Boolean) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
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
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    genderTagEnable = true,
                    recruitmentGender = null,
                ),
            )
        }
    }

    fun updateAgeRangeReset() {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    ageRange = 0f..70f,
                    ageTagEnable = true,
                ),
            )
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    ageRange = ageRange,
                    ageTagEnable = ageRange == 0f..70f,
                ),
            )
        }
    }

    fun updateCareer(career: Career, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
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
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    careerTagEnable = enable,
                    career = if (enable) {
                        null
                    } else {
                        state.actorRecruitingStep1UiModel.career
                    },
                ),
            )
        }
    }

    fun updateProduction(production: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    production = production,
                ),
            )
        }
    }

    fun updateWorkTitle(workTitle: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    workTitle = workTitle,
                ),
            )
        }
    }

    fun updateDirectorName(directorName: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    directorName = directorName,
                ),
            )
        }
    }

    fun updateGenre(genre: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    genre = genre,
                ),
            )
        }
    }

    fun updateLogLine(logLine: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    logLine = logLine,
                ),
            )
        }
    }

    fun updateLogLineTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    isLogLineTagEnable = enable,
                ),
            )
        }
    }

    fun updateLocation(location: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    location = location,
                    locationTagEnable = if (state.actorRecruitingStep3UiModel.locationTagEnable && location.isNotEmpty()) {
                        false
                    } else {
                        state.actorRecruitingStep3UiModel.locationTagEnable
                    },
                ),
            )
        }
    }

    fun updatePeriod(period: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    period = period,
                    periodTagEnable = false,
                ),
            )
        }
    }

    fun updatePay(pay: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    pay = pay,
                    payTagEnable = false,
                ),
            )
        }
    }

    fun updateLocationTagEnable(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    locationTagEnable = enable,
                    location = if (enable) {
                        ""
                    } else {
                        state.actorRecruitingStep3UiModel.location
                    },
                ),
            )
        }
    }

    fun updatePeriodTagEnable(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    periodTagEnable = enable,
                    period = if (enable) {
                        ""
                    } else {
                        state.actorRecruitingStep3UiModel.period
                    },
                ),
            )
        }
    }

    fun updatePayTagEnable(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    payTagEnable = enable,
                    pay = if (enable) {
                        ""
                    } else {
                        state.actorRecruitingStep3UiModel.pay
                    },
                ),
            )
        }
    }

    fun updateDetailInfo(detailInfo: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep4UiModel = state.actorRecruitingStep4UiModel.copy(
                    detailInfo = detailInfo,
                ),
            )
        }
    }

    fun updateManager(manager: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep5UiModel = state.actorRecruitingStep5UiModel.copy(
                    manager = manager,
                ),
            )
        }
    }

    fun updateEmail(email: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep5UiModel = state.actorRecruitingStep5UiModel.copy(
                    email = email,
                ),
            )
        }
    }
}
