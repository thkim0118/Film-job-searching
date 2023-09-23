package com.fone.filmone.ui.recruiting.register.actor

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.jobopenings.Work
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.request.jobopening.JobOpeningsRegisterRequest
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.RegisterJobOpeningUseCase
import com.fone.filmone.ui.recruiting.common.actor.ActorRecruitingViewModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingFocusEvent
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep1UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep2UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep3UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep4UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep5UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingUiEvent
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorRecruitingRegisterViewModel @Inject constructor(
    private val registerJobOpeningUseCase: RegisterJobOpeningUseCase,
) : ActorRecruitingViewModel() {

    override val viewModelState: MutableStateFlow<ActorRecruitingViewModelState> =
        MutableStateFlow(ActorRecruitingRegisterViewModelState())

    override val uiState = viewModelState
        .map(ActorRecruitingViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState(),
        )

    override fun register() = viewModelScope.launch {
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
                casting = step1UiModel.recruitmentActor.ifEmpty { null },
                categories = step1UiModel.categories.map { it.name },
                deadline = if (step1UiModel.deadlineDate == "상시모집") null else step1UiModel.deadlineDate,
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
                    logline = step2UiModel.logLine.ifEmpty { null },
                    location = step3UiModel.location.ifEmpty { null },
                    period = step3UiModel.period.ifEmpty { null },
                    pay = step3UiModel.pay.ifEmpty { null },
                    details = step4UiModel.detailInfo,
                    manager = step5UiModel.manager,
                    email = step5UiModel.email,
                ),
            ),
        ).onSuccess { response ->
            if (response == null) {
                return@onSuccess
            }

            viewModelState.update {
                it.copy(actorRecruitingUiEvent = ActorRecruitingUiEvent.RegisterComplete)
            }
        }.onFail {
            showToast(it.message)
        }
    }
}

private class ActorRecruitingRegisterViewModelState(
    override val actorRecruitingStep1UiModel: ActorRecruitingStep1UiModel = ActorRecruitingStep1UiModel(),
    override val actorRecruitingStep2UiModel: ActorRecruitingStep2UiModel = ActorRecruitingStep2UiModel(),
    override val actorRecruitingStep3UiModel: ActorRecruitingStep3UiModel = ActorRecruitingStep3UiModel(),
    override val actorRecruitingStep4UiModel: ActorRecruitingStep4UiModel = ActorRecruitingStep4UiModel(),
    override val actorRecruitingStep5UiModel: ActorRecruitingStep5UiModel = ActorRecruitingStep5UiModel(),
    override val actorRecruitingUiEvent: ActorRecruitingUiEvent = ActorRecruitingUiEvent.Clear,
    override val focusEvent: ActorRecruitingFocusEvent? = null,
) : ActorRecruitingViewModelState() {
    override fun copy(
        actorRecruitingStep1UiModel: ActorRecruitingStep1UiModel,
        actorRecruitingStep2UiModel: ActorRecruitingStep2UiModel,
        actorRecruitingStep3UiModel: ActorRecruitingStep3UiModel,
        actorRecruitingStep4UiModel: ActorRecruitingStep4UiModel,
        actorRecruitingStep5UiModel: ActorRecruitingStep5UiModel,
        actorRecruitingUiEvent: ActorRecruitingUiEvent,
        focusEvent: ActorRecruitingFocusEvent?,
    ): ActorRecruitingViewModelState = ActorRecruitingRegisterViewModelState(
        actorRecruitingStep1UiModel = actorRecruitingStep1UiModel,
        actorRecruitingStep2UiModel = actorRecruitingStep2UiModel,
        actorRecruitingStep3UiModel = actorRecruitingStep3UiModel,
        actorRecruitingStep4UiModel = actorRecruitingStep4UiModel,
        actorRecruitingStep5UiModel = actorRecruitingStep5UiModel,
        actorRecruitingUiEvent = actorRecruitingUiEvent,
        focusEvent = focusEvent,
    )
}
