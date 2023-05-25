package com.fone.filmone.ui.main.job.recruiting.register.actor

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
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ActorRecruitingRegisterViewModel @Inject constructor(
) : BaseViewModel() {

    private val viewModelState = MutableStateFlow(ActorRecruitingRegisterViewModelState())

    val uiState = viewModelState
        .map(ActorRecruitingRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun updateTitle(title: String) {
        viewModelState.update {
            it.copy(
                step1UiModel = it.step1UiModel.copy(titleText = title)
            )
        }
    }

    fun updateCategory(category: Category, enable: Boolean) {
        viewModelState.update {
            it.copy(
                step1UiModel = it.step1UiModel.copy(
                    categories = if (enable) {
                        it.step1UiModel.categories + setOf(category)
                    } else {
                        it.step1UiModel.categories.filterNot { it == category }.toSet()
                    }
                )
            )
        }
    }

    fun updateDeadlineDate(deadlineDate: String) {
        viewModelState.update {
            it.copy(
                step1UiModel = it.step1UiModel.copy(deadlineDate = deadlineDate)
            )
        }
    }

    fun updateRecruitmentActor(actor: String) {
        viewModelState.update {
            it.copy(
                step1UiModel = it.step1UiModel.copy(recruitmentActor = actor)
            )
        }
    }

    fun updateRecruitmentNumber(number: String) {
        viewModelState.update {
            it.copy(
                step1UiModel = it.step1UiModel.copy(recruitmentNumber = number)
            )
        }
    }

    fun updateRecruitmentGender(gender: Gender, enable: Boolean) {
        viewModelState.update { it ->
            it.copy(
                step1UiModel = it.step1UiModel.copy(
                    recruitmentGender = if (enable) {
                        it.step1UiModel.recruitmentGender + setOf(gender)
                    } else {
                        it.step1UiModel.recruitmentGender
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
                step1UiModel = state.step1UiModel.copy(
                    ageRange = 1f..70f
                )
            )
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(
                step1UiModel = state.step1UiModel.copy(
                    ageRange = ageRange
                )
            )
        }
    }

    fun updateCareer(career: Career, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                step1UiModel = state.step1UiModel.copy(
                    careers = if (enable) {
                        state.step1UiModel.careers + setOf(career)
                    } else {
                        state.step1UiModel.careers
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
                step2UiModel = state.step2UiModel.copy(
                    production = production
                )
            )
        }
    }

    fun updateWorkTitle(workTitle: String) {
        viewModelState.update { state ->
            state.copy(
                step2UiModel = state.step2UiModel.copy(
                    workTitle = workTitle
                )
            )
        }
    }

    fun updateDirectorName(directorName: String) {
        viewModelState.update { state ->
            state.copy(
                step2UiModel = state.step2UiModel.copy(
                    directorName = directorName
                )
            )
        }
    }

    fun updateGenre(genre: String) {
        viewModelState.update { state ->
            state.copy(
                step2UiModel = state.step2UiModel.copy(
                    genre = genre
                )
            )
        }
    }

    fun updateLogLine(logLine: String) {
        viewModelState.update { state ->
            state.copy(
                step2UiModel = state.step2UiModel.copy(
                    logLine = logLine
                )
            )
        }
    }

    fun updateLogLinePrivate() {
        viewModelState.update { state ->
            state.copy(
                step2UiModel = state.step2UiModel.copy(
                    isLogLinePrivate = state.step2UiModel.isLogLinePrivate.not()
                )
            )
        }
    }

    fun updateLocation(location: String) {
        viewModelState.update { state ->
            state.copy(
                step3UiModel = state.step3UiModel.copy(
                    location = location
                )
            )
        }
    }

    fun updatePeriod(period: String) {
        viewModelState.update { state ->
            state.copy(
                step3UiModel = state.step3UiModel.copy(
                    period = period
                )
            )
        }
    }

    fun updatePay(pay: String) {
        viewModelState.update { state ->
            state.copy(
                step3UiModel = state.step3UiModel.copy(
                    pay = pay
                )
            )
        }
    }

    fun updateDetailInfo(detailInfo: String) {
        viewModelState.update { state ->
            state.copy(
                step4UiModel = state.step4UiModel.copy(
                    detailInfo = detailInfo
                )
            )
        }
    }

    fun updateManager(manager: String) {
        viewModelState.update { state ->
            state.copy(
                step5UiModel = state.step5UiModel.copy(
                    manager = manager
                )
            )
        }
    }

    fun updateEmail(email: String) {
        viewModelState.update { state ->
            state.copy(
                step5UiModel = state.step5UiModel.copy(
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
    val step1UiModel: Step1UiModel = Step1UiModel(),
    val step2UiModel: Step2UiModel = Step2UiModel(),
    val step3UiModel: Step3UiModel = Step3UiModel(),
    val step4UiModel: Step4UiModel = Step4UiModel(),
    val step5UiModel: Step5UiModel = Step5UiModel()
) {
    fun toUiState(): ActorRecruitingRegisterUiModel = ActorRecruitingRegisterUiModel(
        step1UiModel = step1UiModel,
        step2UiModel = step2UiModel,
        step3UiModel = step3UiModel,
        step4UiModel = step4UiModel,
        step5UiModel = step5UiModel
    )
}

data class ActorRecruitingRegisterUiModel(
    val step1UiModel: Step1UiModel,
    val step2UiModel: Step2UiModel,
    val step3UiModel: Step3UiModel,
    val step4UiModel: Step4UiModel,
    val step5UiModel: Step5UiModel
)

data class Step1UiModel(
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

data class Step2UiModel(
    val production: String = "",
    val workTitle: String = "",
    val directorName: String = "",
    val genre: String = "",
    val logLine: String = "",
    val logLineTextLimit: Int = 200,
    val isLogLinePrivate: Boolean = false,
)

data class Step3UiModel(
    val location: String = "",
    val period: String = "",
    val pay: String = "",
)

data class Step4UiModel(
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 500,
)

data class Step5UiModel(
    val manager: String = "",
    val email: String = "",
)