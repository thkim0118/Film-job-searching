package com.fone.filmone.ui.main.job

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.domain.usecase.GetJobOpeningsListUseCase
import com.fone.filmone.domain.usecase.GetProfileListUseCase
import com.fone.filmone.domain.usecase.RegisterScrapUseCase
import com.fone.filmone.domain.usecase.WantProfileUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobScreenSharedViewModel @Inject constructor(
    private val getJobOpeningsListUseCase: GetJobOpeningsListUseCase,
    private val getProfileListUseCase: GetProfileListUseCase,
    private val wantProfileUseCase: WantProfileUseCase,
    private val registerScrapUseCase: RegisterScrapUseCase,
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(JobScreenViewModelState())

    val uiState = viewModelState
        .map(JobScreenViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState(),
        )

    fun initUserType(userType: Type, sortingType: JobFilterType) {
        viewModelState.update {
            it.copy(userType = userType)
        }

        val sort = if (sortingType == JobFilterType.Recent) {
            "createdAt,DESC"
        } else if (sortingType == JobFilterType.View) {
            "viewCount,DESC"
        } else {
            "deadline,ASC"
        }
        val initJobTabFilterVo = JobTabFilterVo(
            ageMax = 70,
            ageMin = 0,
            categories = Category.values().toList(),
            domains = Domain.values().toList(),
            genders = Gender.values().toList(),
            type = userType,
            sort = sort
        )

        when (userType) {
            Type.ACTOR -> {
                fetchActorJobOpenings(initJobTabFilterVo, true)
                fetchActorProfile(initJobTabFilterVo, true)
            }

            Type.STAFF -> {
                fetchStaffJobOpenings(initJobTabFilterVo, true)
                fetchStaffProfile(initJobTabFilterVo, true)
            }
        }
    }

    private fun fetchActorJobOpenings(
        jobTabFilterVo: JobTabFilterVo,
        refresh: Boolean,
    ) = viewModelScope.launch {
        getJobOpeningsListUseCase(jobTabFilterVo)
            .onSuccess { response ->
                if (response != null) {
                    viewModelState.update { state ->
                        state.copy(
                            jobOpeningUiModels = if (refresh) {
                                emptyList()
                            } else {
                                state.jobOpeningUiModels
                            } + response.jobOpenings.content.map { content ->
                                JobTabJobOpeningUiModel(
                                    id = content.id,
                                    categories = content.categories,
                                    title = content.title,
                                    deadline = content.deadline ?: R.string.always_recruiting.toString(),
                                    director = content.work.director,
                                    gender = content.gender,
                                    period = content.dday,
                                    jobType = JobType.PART,
                                    casting = content.casting,
                                    isScrap = content.isScrap,
                                    type = content.type,
                                )
                            },
                        )
                    }
                }
            }
    }

    private fun fetchActorProfile(
        jobTabFilterVo: JobTabFilterVo,
        refresh: Boolean,
    ) = viewModelScope.launch {
        getProfileListUseCase(jobTabFilterVo)
            .onSuccess { response ->
                if (response != null) {
                    viewModelState.update { state ->
                        state.copy(
                            profileUiModels = if (refresh) {
                                emptyList()
                            } else {
                                state.profileUiModels
                            } + response.profiles.content.map { content ->
                                JobTabProfilesUiModel(
                                    id = content.id,
                                    profileUrl = content.profileUrl,
                                    name = content.name,
                                    info = "${content.birthday.slice(0..3)}년생 (${content.age}살)",
                                    isWant = content.isWant,
                                    type = content.type,
                                )
                            }
                        )
                    }
                }
            }
    }

    private fun fetchStaffJobOpenings(
        jobTabFilterVo: JobTabFilterVo,
        refresh: Boolean,
    ) = viewModelScope.launch {
        getJobOpeningsListUseCase(jobTabFilterVo)
            .onSuccess { response ->
                if (response != null) {
                    viewModelState.update { state ->
                        state.copy(
                            jobOpeningUiModels = if (refresh) {
                                emptyList()
                            } else {
                                state.jobOpeningUiModels
                            } + response.jobOpenings.content.map { content ->
                                JobTabJobOpeningUiModel(
                                    id = content.id,
                                    categories = content.categories,
                                    title = content.title,
                                    deadline = content.deadline ?: R.string.always_recruiting.toString(),
                                    director = content.work.director,
                                    gender = content.gender,
                                    period = content.dday,
                                    jobType = JobType.Field,
                                    casting = content.casting,
                                    isScrap = content.isScrap,
                                    type = content.type,
                                )
                            },
                        )
                    }
                }
            }
    }

    private fun fetchStaffProfile(
        jobTabFilterVo: JobTabFilterVo,
        refresh: Boolean,
    ) = viewModelScope.launch {
        getProfileListUseCase(jobTabFilterVo)
            .onSuccess { response ->
                if (response != null) {
                    viewModelState.update { state ->
                        state.copy(
                            profileUiModels = if (refresh) {
                                emptyList()
                            } else {
                                state.profileUiModels
                            } + response.profiles.content.map { content ->
                                JobTabProfilesUiModel(
                                    id = content.id,
                                    profileUrl = content.profileUrl,
                                    name = content.name,
                                    info = "${content.birthday.slice(0..3)}년생 (${content.age}살)",
                                    isWant = content.isWant,
                                    type = content.type
                                )
                            }
                        )
                    }
                }
            }
    }

    fun wantProfile(profileId: Int) = viewModelScope.launch {
        wantProfileUseCase(profileId = profileId.toLong())
            .onSuccess {
                viewModelState.update { state ->
                    state.copy(
                        profileUiModels = state.profileUiModels.map { uiModel ->
                            if (uiModel.id == profileId) {
                                uiModel.copy(isWant = uiModel.isWant.not())
                            } else {
                                uiModel
                            }
                        }
                    )
                }
            }
    }

    fun registerScrap(id: Int) = viewModelScope.launch {
        registerScrapUseCase(id)
            .onSuccess {
                viewModelState.update { state ->
                    state.copy(
                        jobOpeningUiModels = state.jobOpeningUiModels.map { uiModel ->
                            if (uiModel.id == id) {
                                uiModel.copy(isScrap = uiModel.isScrap.not())
                            } else {
                                uiModel
                            }
                        }
                    )
                }
            }
    }

    fun updateType(type: Type) {
        viewModelState.update {
            it.copy(userType = type)
        }
    }
}

private data class JobScreenViewModelState(
    val userType: Type? = null,
    val jobOpeningUiModels: List<JobTabJobOpeningUiModel> = emptyList(),
    val profileUiModels: List<JobTabProfilesUiModel> = emptyList(),
    val actorJobOpeningsFilter: JobTabFilterVo = JobTabFilterVo(
        ageMax = 70,
        ageMin = 0,
        categories = emptyList(),
        domains = null,
        genders = emptyList(),
        type = Type.ACTOR,
        sort = "createdAt,DESC"
    ),
    val actorProfilesFilter: JobTabFilterVo = JobTabFilterVo(
        ageMax = 70,
        ageMin = 0,
        categories = emptyList(),
        domains = null,
        genders = emptyList(),
        type = Type.ACTOR,
        sort = "createdAt,DESC"
    ),
    val staffJobOpeningsFilter: JobTabFilterVo = JobTabFilterVo(
        ageMax = 70,
        ageMin = 0,
        categories = emptyList(),
        domains = null,
        genders = emptyList(),
        type = Type.STAFF,
        sort = "createdAt,DESC"
    ),
    val staffProfilesFilter: JobTabFilterVo = JobTabFilterVo(
        ageMax = 70,
        ageMin = 0,
        categories = emptyList(),
        domains = null,
        genders = emptyList(),
        type = Type.STAFF,
        sort = "createdAt,DESC"
    ),
) {
    fun toUiState(): JobScreenUiState = JobScreenUiState(
        userType = userType,
        jobOpeningUiModels = jobOpeningUiModels,
        profileUiModels = profileUiModels,
    )
}

data class JobScreenUiState(
    val userType: Type?,
    val jobOpeningUiModels: List<JobTabJobOpeningUiModel>,
    val profileUiModels: List<JobTabProfilesUiModel>,
)

enum class JobFilterType(@StringRes val titleRes: Int) {
    Recent(R.string.job_tab_main_filter_recent),
    View(R.string.job_tab_main_filter_lookups),
    Deadline(R.string.job_tab_main_filter_due),
}

data class JobTabJobOpeningUiModel(
    val id: Int,
    val categories: List<Category>,
    val title: String,
    val deadline: String,
    val director: String,
    val gender: Gender,
    val period: String,
    val jobType: JobType,
    val casting: String?,
    val isScrap: Boolean,
    val type: Type,
)

data class JobTabProfilesUiModel(
    val id: Int,
    val profileUrl: String,
    val name: String,
    val info: String,
    val isWant: Boolean,
    val type: Type,
)
