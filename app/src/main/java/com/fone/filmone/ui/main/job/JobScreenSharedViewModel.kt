package com.fone.filmone.ui.main.job

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.data.datamodel.common.jobopenings.JobOpenings
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.profile.Profiles
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.domain.usecase.GetJobOpeningsListUseCase
import com.fone.filmone.domain.usecase.GetProfileListUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class JobScreenSharedViewModel @Inject constructor(
    private val getJobOpeningsListUseCase: GetJobOpeningsListUseCase,
    private val getProfileListUseCase: GetProfileListUseCase,
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(JobScreenViewModelState())

    val uiState = viewModelState
        .map(JobScreenViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun initUserType(userType: Type) {
        if (viewModelState.value.userType == null) {
            viewModelState.update {
                it.copy(userType = userType)
            }

            val initJobTabFilterVo = JobTabFilterVo(
                ageMax = 70,
                ageMin = 1,
                categories = Category.values().toList(),
                domains = Domain.values().toList(),
                genders = Gender.values().toList(),
                type = userType
            )

            fetchActorJobOpenings(initJobTabFilterVo)
            fetchActorProfile(initJobTabFilterVo)
            fetchStaffJobOpenings(initJobTabFilterVo)
            fetchStaffProfile(initJobTabFilterVo)
        }
    }

    fun fetchActorJobOpenings(jobTabFilterVo: JobTabFilterVo) = viewModelScope.launch {
        getJobOpeningsListUseCase(jobTabFilterVo)
            .onSuccess { response ->
                if (response != null) {
                    viewModelState.update {
                        it.copy(actorJobOpenings = response.jobOpenings)
                    }
                }
            }
    }

    fun fetchActorProfile(jobTabFilterVo: JobTabFilterVo) = viewModelScope.launch {
        getProfileListUseCase(jobTabFilterVo)
            .onSuccess { response ->
                if (response != null) {
                    viewModelState.update {
                        it.copy(actorProfiles = response.profiles)
                    }
                }
            }
    }

    fun fetchStaffJobOpenings(jobTabFilterVo: JobTabFilterVo) = viewModelScope.launch {
        getJobOpeningsListUseCase(jobTabFilterVo)
            .onSuccess { response ->
                if (response != null) {
                    viewModelState.update {
                        it.copy(staffJobOpenings = response.jobOpenings)
                    }
                }
            }
    }

    fun fetchStaffProfile(jobTabFilterVo: JobTabFilterVo) = viewModelScope.launch {
        getProfileListUseCase(jobTabFilterVo)
            .onSuccess { response ->
                if (response != null) {
                    viewModelState.update {
                        it.copy(staffProfiles = response.profiles)
                    }
                }

                LogUtil.i("response :: $response")
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
    val actorJobOpenings: JobOpenings? = null,
    val actorProfiles: Profiles? = null,
    val staffJobOpenings: JobOpenings? = null,
    val staffProfiles: Profiles? = null,
    val actorJobOpeningsFilter: JobTabFilterVo = JobTabFilterVo(
        ageMax = 70,
        ageMin = 0,
        categories = emptyList(),
        domains = null,
        genders = emptyList(),
        type = Type.ACTOR
    ),
    val actorProfilesFilter: JobTabFilterVo = JobTabFilterVo(
        ageMax = 70,
        ageMin = 0,
        categories = emptyList(),
        domains = null,
        genders = emptyList(),
        type = Type.ACTOR
    ),
    val staffJobOpeningsFilter: JobTabFilterVo = JobTabFilterVo(
        ageMax = 70,
        ageMin = 0,
        categories = emptyList(),
        domains = null,
        genders = emptyList(),
        type = Type.ACTOR
    ),
    val staffProfilesFilter: JobTabFilterVo = JobTabFilterVo(
        ageMax = 70,
        ageMin = 0,
        categories = emptyList(),
        domains = null,
        genders = emptyList(),
        type = Type.ACTOR
    )
) {
    fun toUiState(): JobScreenUiState = JobScreenUiState(
        type = userType,
        jobOpeningsUiModel = actorJobOpenings?.content?.map { content ->
            JobTabJobOpeningUiModel(
                categories = content.categories,
                title = content.title,
                deadline = content.deadline,
                director = content.work.director,
                gender = content.gender,
                period = content.dday,
//                    jobType = JobType.PART, // TODO 어떤 값을 써야하는지 찾아야함.
                jobType = JobType.values()[Random.nextInt(JobType.values().lastIndex)],
                casting = content.casting
            )
        } ?: emptyList(),
        profileUiModels = actorProfiles?.content?.map { content ->
            ProfilesUiModel(
                profileUrl = content.profileUrl,
                name = content.name,
                info = content.birthday + content.age
            )
        } ?: emptyList()
    )
}

data class JobScreenUiState(
    val type: Type?,
    val jobOpeningsUiModel: List<JobTabJobOpeningUiModel>,
    val profileUiModels: List<ProfilesUiModel>,
)

enum class JobFilterType(@StringRes val titleRes: Int) {
    Recent(R.string.job_tab_main_filter_recent),
    View(R.string.job_tab_main_filter_lookups),
    Deadline(R.string.job_tab_main_filter_due)
}

data class JobTabJobOpeningUiModel(
    val categories: List<Category>,
    val title: String,
    val deadline: String,
    val director: String,
    val gender: Gender,
    val period: String,
    val jobType: JobType,
    val casting: String
)

data class ProfilesUiModel(
    val profileUrl: String,
    val name: String,
    val info: String
)