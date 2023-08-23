package com.fone.filmone.ui.recruiting.edit.staff

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
import com.fone.filmone.ui.recruiting.common.staff.StaffRecruitingViewModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingStep1UiModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingStep2UiModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingStep3UiModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingStep4UiModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingStep5UiModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingUiEvent
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingUiModel
import com.fone.filmone.ui.recruiting.common.staff.model.StaffRecruitingViewModelState
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
class StaffRecruitingEditViewModel @Inject constructor(
    private val getJobOpeningDetailUseCase: GetJobOpeningDetailUseCase,
    private val modifyJobOpeningContentUseCase: ModifyJobOpeningContentUseCase,
    savedStateHandle: SavedStateHandle,
) : StaffRecruitingViewModel() {

    override val viewModelState: MutableStateFlow<StaffRecruitingViewModelState> =
        MutableStateFlow(StaffRecruitingEditViewModelState())

    override val uiState: StateFlow<StaffRecruitingUiModel> = viewModelState
        .map(StaffRecruitingViewModelState::toUiState)
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
        savedStateHandle.get<Int>(FOneDestinations.StaffRecruitingEdit.argContentId)

    private fun fetchInitialContent(contentId: Int?) {
        if (contentId != null && contentId > 0) {
            viewModelScope.launch {
                getJobOpeningDetailUseCase(contentId, Type.STAFF)
                    .onSuccess { response ->
                        if (response != null) {
                            val content = response.jobOpening

                            viewModelState.update {
                                it.copy(
                                    staffRecruitingStep1UiModel = StaffRecruitingStep1UiModel(
                                        titleText = content.title,
                                        categories = content.categories.toSet(),
                                        deadlineDate = content.deadline,
                                        deadlineTagEnable = false,
                                        recruitmentDomains = content.domains.toSet(),
                                        recruitmentNumber = content.numberOfRecruits.toString(),
                                        recruitmentGender = if (content.gender == Gender.IRRELEVANT) {
                                            null
                                        } else {
                                            content.gender
                                        },
                                        genderTagEnable = content.gender == Gender.IRRELEVANT,
                                        ageRange = if (content.ageMin == 0 || content.ageMax == 0) {
                                            1f..70f
                                        } else {
                                            content.ageMin.toFloat()..content.ageMax.toFloat()
                                        },
                                        ageTagEnable = content.ageMin == 0 || content.ageMax == 0,
                                        career = if (content.career != Career.IRRELEVANT) {
                                            content.career
                                        } else {
                                            null
                                        },
                                        careerTagEnable = content.career == Career.IRRELEVANT,
                                    ),
                                    staffRecruitingStep2UiModel = StaffRecruitingStep2UiModel(
                                        production = content.work.produce,
                                        workTitle = content.work.workTitle,
                                        directorName = content.work.director,
                                        genre = content.work.genre,
                                        logLine = content.work.logline ?: "",
                                    ),
                                    staffRecruitingStep3UiModel = StaffRecruitingStep3UiModel(
                                        location = content.work.location ?: "",
                                        locationTagEnable = content.work.location == null,
                                        period = content.work.period ?: "",
                                        periodTagEnable = content.work.period == null,
                                        pay = content.work.pay ?: "",
                                        payTagEnable = content.work.pay == null,
                                    ),
                                    staffRecruitingStep4UiModel = StaffRecruitingStep4UiModel(
                                        detailInfo = content.work.details,
                                    ),
                                    staffRecruitingStep5UiModel = StaffRecruitingStep5UiModel(
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
        val step1UiModel = uiState.value.staffRecruitingStep1UiModel
        val step2UiModel = uiState.value.staffRecruitingStep2UiModel
        val step3UiModel = uiState.value.staffRecruitingStep3UiModel
        val step4UiModel = uiState.value.staffRecruitingStep4UiModel
        val step5UiModel = uiState.value.staffRecruitingStep5UiModel

        modifyJobOpeningContentUseCase(
            jobOpeningId = contentId ?: return@launch,
            jobOpeningsRegisterRequest = JobOpeningsRegisterRequest(
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
                ),
            ),
        ).onSuccess { response ->
            if (response == null) {
                return@onSuccess
            }

            viewModelState.update {
                it.copy(staffRecruitingRegisterUiEvent = StaffRecruitingUiEvent.RegisterComplete)
            }
        }.onFail {
            showToast(it.message)
        }
    }
}

private class StaffRecruitingEditViewModelState(
    override val staffRecruitingStep1UiModel: StaffRecruitingStep1UiModel = StaffRecruitingStep1UiModel(),
    override val staffRecruitingStep2UiModel: StaffRecruitingStep2UiModel = StaffRecruitingStep2UiModel(),
    override val staffRecruitingStep3UiModel: StaffRecruitingStep3UiModel = StaffRecruitingStep3UiModel(),
    override val staffRecruitingStep4UiModel: StaffRecruitingStep4UiModel = StaffRecruitingStep4UiModel(),
    override val staffRecruitingStep5UiModel: StaffRecruitingStep5UiModel = StaffRecruitingStep5UiModel(),
    override val registerButtonEnable: Boolean = false,
    override val staffRecruitingRegisterUiEvent: StaffRecruitingUiEvent = StaffRecruitingUiEvent.Clear,
) : StaffRecruitingViewModelState() {
    override fun copy(
        staffRecruitingStep1UiModel: StaffRecruitingStep1UiModel,
        staffRecruitingStep2UiModel: StaffRecruitingStep2UiModel,
        staffRecruitingStep3UiModel: StaffRecruitingStep3UiModel,
        staffRecruitingStep4UiModel: StaffRecruitingStep4UiModel,
        staffRecruitingStep5UiModel: StaffRecruitingStep5UiModel,
        registerButtonEnable: Boolean,
        staffRecruitingRegisterUiEvent: StaffRecruitingUiEvent,
    ): StaffRecruitingViewModelState = StaffRecruitingEditViewModelState(
        staffRecruitingStep1UiModel = staffRecruitingStep1UiModel,
        staffRecruitingStep2UiModel = staffRecruitingStep2UiModel,
        staffRecruitingStep3UiModel = staffRecruitingStep3UiModel,
        staffRecruitingStep4UiModel = staffRecruitingStep4UiModel,
        staffRecruitingStep5UiModel = staffRecruitingStep5UiModel,
        registerButtonEnable = registerButtonEnable,
        staffRecruitingRegisterUiEvent = staffRecruitingRegisterUiEvent,
    )
}
