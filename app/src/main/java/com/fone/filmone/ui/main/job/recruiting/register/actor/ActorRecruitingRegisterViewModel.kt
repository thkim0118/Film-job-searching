package com.fone.filmone.ui.main.job.recruiting.register.actor

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.domain.usecase.RegisterJobOpeningUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ActorRecruitingRegisterViewModel @Inject constructor(
    private val registerJobOpeningUseCase: RegisterJobOpeningUseCase
) : BaseViewModel() {

    private val viewModelState = MutableStateFlow(ActorRecruitingRegisterViewModelState())

    val uiState = viewModelState
        .map(ActorRecruitingRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun registerJobOpening() {

    }

    fun updateTitle(title: String) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(titleText = title)
            )
        }
    }

    fun updateCategory(category: Category, enable: Boolean) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    categories = if (enable) {
                        it.actorRecruitingStep1UiModel.categories + setOf(category)
                    } else {
                        it.actorRecruitingStep1UiModel.categories.filterNot { it == category }
                            .toSet()
                    }
                )
            )
        }
    }

    fun updateDeadlineDate(deadlineDate: String) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(deadlineDate = deadlineDate)
            )
        }
    }

    fun updateRecruitmentActor(actor: String) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(recruitmentActor = actor)
            )
        }
    }

    fun updateRecruitmentNumber(number: String) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(recruitmentNumber = number)
            )
        }
    }

    fun updateRecruitmentGender(gender: Gender, enable: Boolean) {
        viewModelState.update { it ->
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    recruitmentGender = if (enable) {
                        it.actorRecruitingStep1UiModel.recruitmentGender + setOf(gender)
                    } else {
                        it.actorRecruitingStep1UiModel.recruitmentGender
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
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    ageRange = 1f..70f
                )
            )
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    ageRange = ageRange
                )
            )
        }
    }

    fun updateCareer(career: Career, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    careers = if (enable) {
                        state.actorRecruitingStep1UiModel.careers + setOf(career)
                    } else {
                        state.actorRecruitingStep1UiModel.careers
                            .filterNot { it == career }
                            .toSet()
                    }
                )
            )
        }
    }

    fun updateProduction(production: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    production = production
                )
            )
        }
    }

    fun updateWorkTitle(workTitle: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    workTitle = workTitle
                )
            )
        }
    }

    fun updateDirectorName(directorName: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    directorName = directorName
                )
            )
        }
    }

    fun updateGenre(genre: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    genre = genre
                )
            )
        }
    }

    fun updateLogLine(logLine: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    logLine = logLine
                )
            )
        }
    }

    fun updateLogLinePrivate() {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    isLogLinePrivate = state.actorRecruitingStep2UiModel.isLogLinePrivate.not()
                )
            )
        }
    }

    fun updateLocation(location: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    location = location
                )
            )
        }
    }

    fun updatePeriod(period: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    period = period
                )
            )
        }
    }

    fun updatePay(pay: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    pay = pay
                )
            )
        }
    }

    fun updateLocationTagEnable() {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    locationTagEnable = state.actorRecruitingStep3UiModel.locationTagEnable.not()
                )
            )
        }
    }

    fun updatePeriodTagEnable() {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    periodTagEnable = state.actorRecruitingStep3UiModel.periodTagEnable.not()
                )
            )
        }
    }

    fun updatePayTagEnable() {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    payTagEnable = state.actorRecruitingStep3UiModel.payTagEnable.not()
                )
            )
        }
    }

    fun updateDetailInfo(detailInfo: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep4UiModel = state.actorRecruitingStep4UiModel.copy(
                    detailInfo = detailInfo
                )
            )
        }
    }

    fun updateManager(manager: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep5UiModel = state.actorRecruitingStep5UiModel.copy(
                    manager = manager
                )
            )
        }
    }

    fun updateEmail(email: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep5UiModel = state.actorRecruitingStep5UiModel.copy(
                    email = email
                )
            )
        }
    }

    private fun isDeadlineDateValid(birthday: String): Boolean {
        val birthDayPattern = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0\\d|[1-2]\\d|3[0-1])+$")
        return birthDayPattern.matcher(birthday).matches()
    }
}

private data class ActorRecruitingRegisterViewModelState(
    val actorRecruitingStep1UiModel: ActorRecruitingStep1UiModel = ActorRecruitingStep1UiModel(),
    val actorRecruitingStep2UiModel: ActorRecruitingStep2UiModel = ActorRecruitingStep2UiModel(),
    val actorRecruitingStep3UiModel: ActorRecruitingStep3UiModel = ActorRecruitingStep3UiModel(),
    val actorRecruitingStep4UiModel: ActorRecruitingStep4UiModel = ActorRecruitingStep4UiModel(),
    val actorRecruitingStep5UiModel: ActorRecruitingStep5UiModel = ActorRecruitingStep5UiModel()
) {
    fun toUiState(): ActorRecruitingRegisterUiModel = ActorRecruitingRegisterUiModel(
        actorRecruitingStep1UiModel = actorRecruitingStep1UiModel,
        actorRecruitingStep2UiModel = actorRecruitingStep2UiModel,
        actorRecruitingStep3UiModel = actorRecruitingStep3UiModel,
        actorRecruitingStep4UiModel = actorRecruitingStep4UiModel,
        actorRecruitingStep5UiModel = actorRecruitingStep5UiModel
    )
}

data class ActorRecruitingRegisterUiModel(
    val actorRecruitingStep1UiModel: ActorRecruitingStep1UiModel,
    val actorRecruitingStep2UiModel: ActorRecruitingStep2UiModel,
    val actorRecruitingStep3UiModel: ActorRecruitingStep3UiModel,
    val actorRecruitingStep4UiModel: ActorRecruitingStep4UiModel,
    val actorRecruitingStep5UiModel: ActorRecruitingStep5UiModel
)

data class ActorRecruitingStep1UiModel(
    val titleText: String = "",
    val titleTextLimit: Int = 50,
    val categories: Set<Category> = emptySet(),
    val deadlineDate: String = "",
    val recruitmentActor: String = "",
    val recruitmentNumber: String = "",
    val recruitmentGender: Set<Gender> = emptySet(),
    val ageRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val defaultAgeRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val careers: Set<Career> = emptySet()
)

data class ActorRecruitingStep2UiModel(
    val production: String = "",
    val workTitle: String = "",
    val directorName: String = "",
    val genre: String = "",
    val logLine: String = "",
    val logLineTextLimit: Int = 200,
    val isLogLinePrivate: Boolean = false,
)

data class ActorRecruitingStep3UiModel(
    val location: String = "",
    val period: String = "",
    val pay: String = "",
    val locationTagEnable: Boolean = false,
    val periodTagEnable: Boolean = false,
    val payTagEnable: Boolean = false,
)

data class ActorRecruitingStep4UiModel(
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 500,
)

data class ActorRecruitingStep5UiModel(
    val manager: String = "",
    val email: String = "",
)