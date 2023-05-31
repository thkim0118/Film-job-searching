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
        if (viewModelState.value.actorRecruitingStep1UiModel.categories.size >= 2) {
            return
        }

        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    categories = if (enable) {
                        state.actorRecruitingStep1UiModel.categories + setOf(category)
                    } else {
                        state.actorRecruitingStep1UiModel.categories.filterNot { it == category }
                            .toSet()
                    }
                )
            )
        }
    }

    fun updateDeadlineDate(deadlineDate: String) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    deadlineDate = deadlineDate,
                    deadlineDateTagEnable = if (it.actorRecruitingStep1UiModel.deadlineDateTagEnable && deadlineDate.isNotEmpty()) {
                        false
                    } else {
                        it.actorRecruitingStep1UiModel.deadlineDateTagEnable
                    }
                )
            )
        }
    }

    fun updateDeadlineTag(enable: Boolean) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    deadlineDateTagEnable = enable,
                    deadlineDate = if (enable) {
                        ""
                    } else {
                        it.actorRecruitingStep1UiModel.deadlineDate
                    }
                )
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
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    recruitmentGender = if (enable) {
                        it.actorRecruitingStep1UiModel.recruitmentGender + setOf(gender)
                    } else {
                        it.actorRecruitingStep1UiModel.recruitmentGender
                            .filterNot { recruitmentGender -> recruitmentGender == gender }
                            .toSet()
                    },
                    genderTagEnable = if (enable && it.actorRecruitingStep1UiModel.recruitmentGender.isEmpty()) {
                        false
                    } else {
                        it.actorRecruitingStep1UiModel.genderTagEnable
                    }
                )
            )
        }
    }

    fun updateGenderTag(enable: Boolean) {
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    genderTagEnable = enable,
                    recruitmentGender = if (enable) {
                        emptySet()
                    } else {
                        it.actorRecruitingStep1UiModel.recruitmentGender
                    }
                )
            )
        }
    }

    fun updateAgeRangeReset() {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    ageRange = 1f..70f,
                    ageTagEnable = true
                )
            )
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    ageRange = ageRange,
                    ageTagEnable = ageRange == 1f..70f
                )
            )
        }
    }

    fun updateCareer(career: Career, enable: Boolean) {
        viewModelState.update { state ->
            val restCareer = state.actorRecruitingStep1UiModel.careers
                .filterNot { it == career }
                .toSet()

            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    careers = if (enable) {
                        state.actorRecruitingStep1UiModel.careers + setOf(career)
                    } else {
                        restCareer
                    },
                    careerTagEnable = if (enable) {
                        false
                    } else {
                        if (restCareer.isEmpty()) {
                            true
                        } else {
                            state.actorRecruitingStep1UiModel.careerTagEnable
                        }
                    }
                )
            )
        }
    }

    fun updateCareerTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    careerTagEnable = enable,
                    careers = if (enable) {
                        emptySet()
                    } else {
                        state.actorRecruitingStep1UiModel.careers
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

    fun updateLogLineTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep2UiModel = state.actorRecruitingStep2UiModel.copy(
                    isLogLineTagEnable = enable
                )
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
                    }
                )
            )
        }
    }

    fun updatePeriod(period: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    period = period,
                    periodTagEnable = period.isNotEmpty()
                )
            )
        }
    }

    fun updatePay(pay: String) {
        viewModelState.update { state ->
            state.copy(
                actorRecruitingStep3UiModel = state.actorRecruitingStep3UiModel.copy(
                    pay = pay,
                    payTagEnable = pay.isNotEmpty()
                )
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
                    }
                )
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
                    }
                )
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
                    }
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
    val deadlineDateTagEnable: Boolean = true,
    val recruitmentActor: String = "",
    val recruitmentNumber: String = "",
    val recruitmentGender: Set<Gender> = emptySet(),
    val genderTagEnable: Boolean = true,
    val ageRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val ageTagEnable: Boolean = true,
    val defaultAgeRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val careers: Set<Career> = emptySet(),
    val careerTagEnable: Boolean = true,
)

data class ActorRecruitingStep2UiModel(
    val production: String = "",
    val workTitle: String = "",
    val directorName: String = "",
    val genre: String = "",
    val logLine: String = "",
    val logLineTextLimit: Int = 200,
    val isLogLineTagEnable: Boolean = false,
)

data class ActorRecruitingStep3UiModel(
    val location: String = "",
    val period: String = "",
    val pay: String = "",
    val locationTagEnable: Boolean = true,
    val periodTagEnable: Boolean = true,
    val payTagEnable: Boolean = true,
)

data class ActorRecruitingStep4UiModel(
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 500,
)

data class ActorRecruitingStep5UiModel(
    val manager: String = "",
    val email: String = "",
)