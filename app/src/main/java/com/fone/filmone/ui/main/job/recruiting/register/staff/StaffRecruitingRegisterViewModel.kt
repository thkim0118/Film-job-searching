package com.fone.filmone.ui.main.job.recruiting.register.staff

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
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class StaffRecruitingRegisterViewModel @Inject constructor(
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

    fun register() {
        val state = viewModelState.value

        if (state.registerButtonEnable.not()) {
            return
        }
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
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(deadlineDate = deadlineDate)
            )
        }

        updateRegisterButtonState()
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
                        state.staffRecruitingStep1UiModel.recruitmentGender + setOf(gender)
                    } else {
                        state.staffRecruitingStep1UiModel.recruitmentGender
                            .filterNot { recruitmentGender -> recruitmentGender == gender }
                            .toSet()
                    }
                )
            )
        }
    }

    fun updateAgeRangeReset() {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    ageRange = 1f..70f
                )
            )
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep1UiModel = state.staffRecruitingStep1UiModel.copy(
                    ageRange = ageRange
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
                    location = location
                )
            )
        }
    }

    fun updatePeriod(period: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    period = period
                )
            )
        }
    }

    fun updatePay(pay: String) {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    pay = pay
                )
            )
        }
    }

    fun updateLocationTagEnable() {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    locationTagEnable = state.staffRecruitingStep3UiModel.locationTagEnable.not()
                )
            )
        }
    }

    fun updatePeriodTagEnable() {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    periodTagEnable = state.staffRecruitingStep3UiModel.periodTagEnable.not()
                )
            )
        }
    }

    fun updatePayTagEnable() {
        viewModelState.update { state ->
            state.copy(
                staffRecruitingStep3UiModel = state.staffRecruitingStep3UiModel.copy(
                    payTagEnable = state.staffRecruitingStep3UiModel.payTagEnable.not()
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
) {
    fun toUiState(): StaffRecruitingRegisterUiModel =
        StaffRecruitingRegisterUiModel(
            staffRecruitingStep1UiModel = staffRecruitingStep1UiModel,
            staffRecruitingStep2UiModel = staffRecruitingStep2UiModel,
            staffRecruitingStep3UiModel = staffRecruitingStep3UiModel,
            staffRecruitingStep4UiModel = staffRecruitingStep4UiModel,
            staffRecruitingStep5UiModel = staffRecruitingStep5UiModel,
            registerButtonEnable = registerButtonEnable,
        )
}

data class StaffRecruitingRegisterUiModel(
    val staffRecruitingStep1UiModel: StaffRecruitingStep1UiModel,
    val staffRecruitingStep2UiModel: StaffRecruitingStep2UiModel,
    val staffRecruitingStep3UiModel: StaffRecruitingStep3UiModel,
    val staffRecruitingStep4UiModel: StaffRecruitingStep4UiModel,
    val staffRecruitingStep5UiModel: StaffRecruitingStep5UiModel,
    val registerButtonEnable: Boolean,
)

data class StaffRecruitingStep1UiModel(
    val titleText: String = "",
    val titleTextLimit: Int = 50,
    val categories: Set<Category> = emptySet(),
    val deadlineDate: String = "",
    val recruitmentDomains: Set<Domain> = emptySet(),
    val recruitmentNumber: String = "",
    val recruitmentGender: Set<Gender> = emptySet(),
    val ageRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val defaultAgeRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val career: Career? = null
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
    val locationTagEnable: Boolean = false,
    val periodTagEnable: Boolean = false,
    val payTagEnable: Boolean = false,
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