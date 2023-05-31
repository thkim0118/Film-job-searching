package com.fone.filmone.ui.main.job.recruiting.register.actor

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.jobopenings.Work
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.request.jobopening.JobOpeningsRegisterRequest
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.RegisterJobOpeningUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        val isValidationPassed = checkValidation()

        if (isValidationPassed) {
            register()
        } else {
            return
        }
    }

    private fun register() = viewModelScope.launch {
        val step1UiModel = uiState.value.actorRecruitingStep1UiModel
        val step2UiModel = uiState.value.actorRecruitingStep2UiModel
        val step3UiModel = uiState.value.actorRecruitingStep3UiModel
        val step4UiModel = uiState.value.actorRecruitingStep4UiModel
        val step5UiModel = uiState.value.actorRecruitingStep5UiModel

        registerJobOpeningUseCase(
            JobOpeningsRegisterRequest(
                ageMax = step1UiModel.ageRange.endInclusive.toInt(),
                ageMin = step1UiModel.ageRange.start.toInt(),
                career = step1UiModel.career ?: Career.IRRELEVANT,
                casting = step1UiModel.recruitmentActor,
                categories = step1UiModel.categories.map { it.name },
                deadline = step1UiModel.deadlineDate,
                domains = null,
                gender = step1UiModel.recruitmentGender ?: Gender.IRRELEVANT,
                numberOfRecruits = step1UiModel.recruitmentNumber.toInt(),
                title = step1UiModel.titleText,
                type = Type.ACTOR,
                work = Work(
                    produce = step2UiModel.production,
                    workTitle = step2UiModel.workTitle,
                    director = step2UiModel.directorName,
                    genre = step2UiModel.genre,
                    logline = step2UiModel.logLine,
                    location = step3UiModel.location,
                    period = step3UiModel.period,
                    pay = step3UiModel.pay,
                    details = step4UiModel.detailInfo,
                    manager = step5UiModel.manager,
                    email = step5UiModel.email,
                )
            )
        ).onSuccess { response ->
            if (response == null) {
                return@onSuccess
            }

            LogUtil.d("onSuccess :: ${response.jobOpening}")
        }.onFail {
            LogUtil.e("fail :: $it")
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

        if (step1UiModel.deadlineDateTagEnable.not() && step1UiModel.deadlineDate.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        if (step1UiModel.deadlineDateTagEnable && isDeadlineDateInvalid(step1UiModel.deadlineDate)) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        if (step1UiModel.categories.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        if (step1UiModel.recruitmentNumber.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        return false
    }

    private fun isDeadlineDateInvalid(birthday: String): Boolean {
        val birthDayPattern = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0\\d|[1-2]\\d|3[0-1])+$")
        return birthDayPattern.matcher(birthday).matches()
    }

    private fun isInvalidateStep2(): Boolean {
        val step2UiModel = uiState.value.actorRecruitingStep2UiModel

        if (step2UiModel.production.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        if (step2UiModel.workTitle.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        if (step2UiModel.directorName.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        if (step2UiModel.genre.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        return false
    }

    private fun isInvalidateStep4(): Boolean {
        val step4UiModel = uiState.value.actorRecruitingStep4UiModel

        if (step4UiModel.detailInfo.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        return false
    }

    private fun isInvalidateStep5(): Boolean {
        val step5UiModel = uiState.value.actorRecruitingStep5UiModel

        if (step5UiModel.manager.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        if (step5UiModel.email.isEmpty()) {
            showToast(R.string.toast_recruiting_register_fail)
            return true
        }

        return false
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
        viewModelState.update {
            it.copy(
                actorRecruitingStep1UiModel = it.actorRecruitingStep1UiModel.copy(
                    genderTagEnable = enable,
                    recruitmentGender = if (enable) {
                        null
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
            state.copy(
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
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
                actorRecruitingStep1UiModel = state.actorRecruitingStep1UiModel.copy(
                    careerTagEnable = enable,
                    career = if (enable) {
                        null
                    } else {
                        state.actorRecruitingStep1UiModel.career
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
    val recruitmentGender: Gender? = null,
    val genderTagEnable: Boolean = true,
    val ageRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val ageTagEnable: Boolean = true,
    val defaultAgeRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val career: Career? = null,
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