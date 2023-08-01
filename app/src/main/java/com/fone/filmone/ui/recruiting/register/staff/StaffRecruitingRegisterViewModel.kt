package com.fone.filmone.ui.recruiting.register.staff

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.jobopenings.Work
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.request.jobopening.JobOpeningsRegisterRequest
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.RegisterJobOpeningUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffRecruitingRegisterViewModel @Inject constructor(
    private val registerJobOpeningUseCase: RegisterJobOpeningUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(StaffRecruitingRegisterViewModelState())

    val uiState = viewModelState
        .map(StaffRecruitingRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private val _dialogState =
        MutableStateFlow<StaffRecruitingRegisterDialogState>(StaffRecruitingRegisterDialogState.Clear)
    val dialogState: StateFlow<StaffRecruitingRegisterDialogState> = _dialogState.asStateFlow()

    fun registerJobOpening() {
        val state = viewModelState.value

        if (state.registerButtonEnable.not()) {
            return
        }

        val isValidationPassed = checkValidation()

        if (isValidationPassed) {
            register()
        } else {
            showToast(R.string.toast_recruiting_register_fail)
            return
        }
    }

    fun register() = viewModelScope.launch {
        val step1UiModel = uiState.value.staffRecruitingStep1UiModel
        val step2UiModel = uiState.value.staffRecruitingStep2UiModel
        val step3UiModel = uiState.value.staffRecruitingStep3UiModel
        val step4UiModel = uiState.value.staffRecruitingStep4UiModel
        val step5UiModel = uiState.value.staffRecruitingStep5UiModel

        registerJobOpeningUseCase(
            JobOpeningsRegisterRequest(
                ageMax = step1UiModel.ageRange.endInclusive.toInt(),
                ageMin = step1UiModel.ageRange.start.toInt(),
                career = step1UiModel.career ?: Career.IRRELEVANT,
                casting = null,
                categories = step1UiModel.categories.map { it.name },
                deadline = step1UiModel.deadlineDate,
                domains = null,
                gender = step1UiModel.recruitmentGender ?: Gender.IRRELEVANT,
                numberOfRecruits = step1UiModel.recruitmentNumber.toInt(),
                title = step1UiModel.titleText,
                type = Type.STAFF,
                work = Work(
                    produce = step2UiModel.production,
                    workTitle = step2UiModel.workTitle,
                    director = step2UiModel.directorName,
                    genre = step2UiModel.genre,
                    logline = step2UiModel.logLine.ifEmpty { null },
                    location = step3UiModel.location.ifEmpty { null },
                    period = step3UiModel.period.ifEmpty { null },
                    pay = step3UiModel.pay.ifEmpty { null },
                    details = step4UiModel.detailInfo,
                    manager = step5UiModel.manager,
                    email = step5UiModel.email,
                )
            )
        ).onSuccess { response ->
            if (response == null) {
                return@onSuccess
            }

            viewModelState.update {
                it.copy(staffRecruitingRegisterUiEvent = StaffRecruitingRegisterUiEvent.RegisterComplete)
            }
        }.onFail {
            showToast(it.message)
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
        val step1UiModel = uiState.value.staffRecruitingStep1UiModel

        if (step1UiModel.titleText.isEmpty()) {
            return true
        }

        if (step1UiModel.deadlineTagEnable.not() && step1UiModel.deadlineDate.isEmpty()) {
            return true
        }

        if (step1UiModel.deadlineTagEnable && PatternUtil.isValidDate(step1UiModel.deadlineDate)
                .not()
        ) {
            return true
        }

        if (step1UiModel.categories.isEmpty()) {
            return true
        }

        if (step1UiModel.recruitmentDomains.isEmpty()) {
            return true
        }

        if (step1UiModel.recruitmentNumber.isEmpty()) {
            return true
        }

        return false
    }

    private fun isInvalidateStep2(): Boolean {
        val step2UiModel = uiState.value.staffRecruitingStep2UiModel

        if (step2UiModel.production.isEmpty()) {
            return true
        }

        if (step2UiModel.workTitle.isEmpty()) {
            return true
        }

        if (step2UiModel.directorName.isEmpty()) {
            return true
        }

        if (step2UiModel.genre.isEmpty()) {
            return true
        }

        return false
    }

    private fun isInvalidateStep4(): Boolean {
        val step4UiModel = uiState.value.staffRecruitingStep4UiModel

        if (step4UiModel.detailInfo.isEmpty()) {
            return true
        }

        return false
    }

    private fun isInvalidateStep5(): Boolean {
        val step5UiModel = uiState.value.staffRecruitingStep5UiModel

        if (step5UiModel.manager.isEmpty()) {
            return true
        }

        if (step5UiModel.email.isEmpty()) {
            return true
        }

        return false
    }

    fun updateDialogState(dialogState: StaffRecruitingRegisterDialogState) {
        _dialogState.value = dialogState
    }

    fun updateTitle(title: String) {
        viewModelState.update {
            it.copy(
                staffRecruitingStep1UiModel = it.staffRecruitingStep1UiModel.copy(titleText = title)
            )
        }

        updateRegisterButtonState()
    }

    fun updateCategory(category: Category, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    categories = if (enable) {
                        state.staffRecruitingStep1UiModel.categories + setOf(category)
                    } else {
                        state.staffRecruitingStep1UiModel.categories.filterNot { it == category }
                            .toSet()
                    }
                )
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
                    }
                )
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
                    }
                )
            )
        }
    }

    fun updateRecruitmentDomains(domains: Set<Domain>) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    recruitmentDomains = domains
                )
            )
        }

        updateRegisterButtonState()
    }

    fun updateRecruitmentNumber(number: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    recruitmentNumber = number
                )
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
                    genderTagEnable = enable.not()
                )
            )
        }
    }

    fun updateGenderTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    genderTagEnable = enable,
                    recruitmentGender = if (enable) {
                        null
                    } else {
                        state.staffRecruitingStep1UiModel.recruitmentGender
                    }
                )
            )
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    ageRange = ageRange,
                    ageTagEnable = ageRange == state.staffRecruitingStep1UiModel.defaultAgeRange
                )
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
                    }
                )
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
                    careerTagEnable = enable.not()
                )
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
                    }
                )
            )
        }
    }

    fun updateProduction(production: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    production = production
                )
            )
        }

        updateRegisterButtonState()
    }

    fun updateWorkTitle(workTitle: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    workTitle = workTitle
                )
            )
        }

        updateRegisterButtonState()
    }

    fun updateDirectorName(directorName: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    directorName = directorName
                )
            )
        }

        updateRegisterButtonState()
    }

    fun updateGenre(genre: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    genre = genre
                )
            )
        }

        updateRegisterButtonState()
    }

    fun updateLogLine(logLine: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    logLine = logLine
                )
            )
        }
    }

    fun updateLogLinePrivate() {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep2UiModel = state.staffRecruitingStep2UiModel.copy(
                    isLogLinePrivate = state.staffRecruitingStep2UiModel.isLogLinePrivate.not()
                )
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
                    }
                )
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
                    }
                )
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
                    }
                )
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
                    }
                )
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
                    }
                )
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
                    }
                )
            )
        }
    }

    fun updateDetailInfo(detailInfo: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep4UiModel = state.staffRecruitingStep4UiModel.copy(
                    detailInfo = detailInfo
                )
            )
        }

        updateRegisterButtonState()
    }

    fun updateManager(manager: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep5UiModel = state.staffRecruitingStep5UiModel.copy(
                    manager = manager
                )
            )
        }

        updateRegisterButtonState()
    }

    fun updateEmail(email: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep5UiModel = state.staffRecruitingStep5UiModel.copy(
                    email = email
                )
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

private data class StaffRecruitingRegisterViewModelState(
    val staffRecruitingStep1UiModel: StaffRecruitingStep1UiModel = StaffRecruitingStep1UiModel(),
    val staffRecruitingStep2UiModel: StaffRecruitingStep2UiModel = StaffRecruitingStep2UiModel(),
    val staffRecruitingStep3UiModel: StaffRecruitingStep3UiModel = StaffRecruitingStep3UiModel(),
    val staffRecruitingStep4UiModel: StaffRecruitingStep4UiModel = StaffRecruitingStep4UiModel(),
    val staffRecruitingStep5UiModel: StaffRecruitingStep5UiModel = StaffRecruitingStep5UiModel(),
    val registerButtonEnable: Boolean = false,
    val staffRecruitingRegisterUiEvent: StaffRecruitingRegisterUiEvent = StaffRecruitingRegisterUiEvent.Clear
) {
    fun toUiState(): StaffRecruitingRegisterUiModel =
        StaffRecruitingRegisterUiModel(
            staffRecruitingStep1UiModel = staffRecruitingStep1UiModel,
            staffRecruitingStep2UiModel = staffRecruitingStep2UiModel,
            staffRecruitingStep3UiModel = staffRecruitingStep3UiModel,
            staffRecruitingStep4UiModel = staffRecruitingStep4UiModel,
            staffRecruitingStep5UiModel = staffRecruitingStep5UiModel,
            registerButtonEnable = registerButtonEnable,
            staffRecruitingRegisterUiEvent = staffRecruitingRegisterUiEvent,
        )
}

data class StaffRecruitingRegisterUiModel(
    val staffRecruitingStep1UiModel: StaffRecruitingStep1UiModel,
    val staffRecruitingStep2UiModel: StaffRecruitingStep2UiModel,
    val staffRecruitingStep3UiModel: StaffRecruitingStep3UiModel,
    val staffRecruitingStep4UiModel: StaffRecruitingStep4UiModel,
    val staffRecruitingStep5UiModel: StaffRecruitingStep5UiModel,
    val registerButtonEnable: Boolean,
    val staffRecruitingRegisterUiEvent: StaffRecruitingRegisterUiEvent
)

data class StaffRecruitingStep1UiModel(
    val titleText: String = "",
    val titleTextLimit: Int = 50,
    val categories: Set<Category> = emptySet(),
    val deadlineDate: String = "",
    val deadlineTagEnable: Boolean = true,
    val recruitmentDomains: Set<Domain> = emptySet(),
    val recruitmentNumber: String = "",
    val recruitmentGender: Gender? = null,
    val genderTagEnable: Boolean = true,
    val ageRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val ageTagEnable: Boolean = true,
    val defaultAgeRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val career: Career? = null,
    val careerTagEnable: Boolean = true,
)

data class StaffRecruitingStep2UiModel(
    val production: String = "",
    val workTitle: String = "",
    val directorName: String = "",
    val genre: String = "",
    val logLine: String = "",
    val logLineTextLimit: Int = 200,
    val isLogLinePrivate: Boolean = false,
)

data class StaffRecruitingStep3UiModel(
    val location: String = "",
    val period: String = "",
    val pay: String = "",
    val locationTagEnable: Boolean = true,
    val periodTagEnable: Boolean = true,
    val payTagEnable: Boolean = true,
)

data class StaffRecruitingStep4UiModel(
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 500,
)

data class StaffRecruitingStep5UiModel(
    val manager: String = "",
    val email: String = "",
)

sealed class StaffRecruitingRegisterDialogState {
    object Clear : StaffRecruitingRegisterDialogState()
    data class DomainSelectDialog(
        val selectedDomains: List<Domain>
    ) : StaffRecruitingRegisterDialogState()
}

sealed class StaffRecruitingRegisterUiEvent {
    object RegisterComplete : StaffRecruitingRegisterUiEvent()
    object Clear : StaffRecruitingRegisterUiEvent()
}