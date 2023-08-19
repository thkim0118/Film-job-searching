package com.fone.filmone.ui.recruiting.edit.actor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.jobopenings.Work
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.request.jobopening.JobOpeningsRegisterRequest
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetJobOpeningDetailUseCase
import com.fone.filmone.domain.usecase.ModifyJobOpeningContentUseCase
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.recruiting.common.actor.ActorRecruitingViewModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingFocusEvent
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep1UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep2UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep3UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep4UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingStep5UiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingUiEvent
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingUiModel
import com.fone.filmone.ui.recruiting.common.actor.model.ActorRecruitingViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorRecruitingEditViewModel @Inject constructor(
    private val getJobOpeningDetailUseCase: GetJobOpeningDetailUseCase,
    private val modifyJobOpeningContentUseCase: ModifyJobOpeningContentUseCase,
    savedStateHandle: SavedStateHandle,
) : ActorRecruitingViewModel() {
    override val viewModelState: MutableStateFlow<ActorRecruitingViewModelState> =
        MutableStateFlow(ActorRecruitingEditViewModelState())
    override val uiState: StateFlow<ActorRecruitingUiModel> =
        viewModelState.map(ActorRecruitingViewModelState::toUiState)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                viewModelState.value.toUiState(),
            )

    private val contentId: Int?

    init {
        contentId = getContentId(savedStateHandle = savedStateHandle)

        fetchInitialContent(contentId)
    }

    private fun getContentId(savedStateHandle: SavedStateHandle) =
        savedStateHandle.get<Int>(FOneDestinations.ActorRecruitingEdit.argContentId)

    private fun fetchInitialContent(contentId: Int?) {
        if (contentId != null && contentId > 0) {
            viewModelScope.launch {
                getJobOpeningDetailUseCase(contentId, Type.ACTOR)
                    .onSuccess { response ->
                        if (response != null) {
                            val content = response.jobOpening

                            viewModelState.update {
                                it.copy(
                                    actorRecruitingStep1UiModel = ActorRecruitingStep1UiModel(
                                        titleText = content.title,
                                        categories = content.categories,
                                        deadlineDate = content.deadline,
                                        deadlineTagEnable = false,
                                        recruitmentActor = content.casting ?: "",
                                        recruitmentNumber = content.numberOfRecruits.toString(),
                                        recruitmentGender = if (content.gender == Gender.IRRELEVANT) {
                                            null
                                        } else {
                                            content.gender
                                        },
                                        genderTagEnable = content.gender == Gender.IRRELEVANT,
                                        ageRange = content.ageMin.toFloat()..content.ageMax.toFloat(),
                                        ageTagEnable = content.ageMin == 1 && content.ageMax == 70,
                                        career = if (content.career != Career.IRRELEVANT) {
                                            content.career
                                        } else {
                                            null
                                        },
                                        careerTagEnable = content.career == Career.IRRELEVANT,
                                    ),
                                    actorRecruitingStep2UiModel = ActorRecruitingStep2UiModel(
                                        production = content.work.produce,
                                        workTitle = content.work.workTitle,
                                        directorName = content.work.director,
                                        genre = content.work.genre,
                                        logLine = content.work.logline ?: "",
                                    ),
                                    actorRecruitingStep3UiModel = ActorRecruitingStep3UiModel(
                                        location = content.work.location ?: "",
                                        locationTagEnable = content.work.location == null,
                                        period = content.work.period ?: "",
                                        periodTagEnable = content.work.period == null,
                                        pay = content.work.pay ?: "",
                                        payTagEnable = content.work.pay == null,
                                    ),
                                    actorRecruitingStep4UiModel = ActorRecruitingStep4UiModel(
                                        detailInfo = content.work.details,
                                    ),
                                    actorRecruitingStep5UiModel = ActorRecruitingStep5UiModel(
                                        manager = content.work.manager,
                                        email = content.work.email,
                                    ),
                                )
                            }
                        }
                    }
            }
        }
    }

    override fun register(): Job = viewModelScope.launch {
        val step1UiModel = uiState.value.actorRecruitingStep1UiModel
        val step2UiModel = uiState.value.actorRecruitingStep2UiModel
        val step3UiModel = uiState.value.actorRecruitingStep3UiModel
        val step4UiModel = uiState.value.actorRecruitingStep4UiModel
        val step5UiModel = uiState.value.actorRecruitingStep5UiModel

        modifyJobOpeningContentUseCase(
            jobOpeningId = contentId ?: return@launch,
            jobOpeningsRegisterRequest = JobOpeningsRegisterRequest(
                ageMax = step1UiModel.ageRange.endInclusive.toInt(),
                ageMin = step1UiModel.ageRange.start.toInt(),
                career = step1UiModel.career ?: Career.IRRELEVANT,
                casting = step1UiModel.recruitmentActor.ifEmpty { null },
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

private class ActorRecruitingEditViewModelState(
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
    ): ActorRecruitingViewModelState = ActorRecruitingEditViewModelState(
        actorRecruitingStep1UiModel = actorRecruitingStep1UiModel,
        actorRecruitingStep2UiModel = actorRecruitingStep2UiModel,
        actorRecruitingStep3UiModel = actorRecruitingStep3UiModel,
        actorRecruitingStep4UiModel = actorRecruitingStep4UiModel,
        actorRecruitingStep5UiModel = actorRecruitingStep5UiModel,
        actorRecruitingUiEvent = actorRecruitingUiEvent,
        focusEvent = focusEvent,
    )
}