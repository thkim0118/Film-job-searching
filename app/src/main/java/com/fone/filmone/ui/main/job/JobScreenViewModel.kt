package com.fone.filmone.ui.main.job

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetUserInfoUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobScreenViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(JobScreenViewModelState())

    val uiState = viewModelState
        .map(JobScreenViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            getUserInfoUseCase()
                .onSuccess { userResponse ->
                    if (userResponse == null) {
                        return@onSuccess
                    }

                    viewModelState.update {
                        it.copy(
                            userJob = userResponse.user.job
                        )
                    }
                }
        }
    }

    fun updateType(type: Type) {
        viewModelState.update {
            it.copy(
                userJob = when (type) {
                    Type.ACTOR -> Job.ACTOR
                    Type.STAFF -> Job.STAFF
                }
            )
        }
    }

    fun updateCurrentJobFilter(jobTab: JobTab) {
        viewModelState.update {
            when (jobTab) {
                JobTab.JOB_OPENING -> {
                    it.copy(
                        currentJobFilter = it.jobOpeningsFilter
                    )
                }
                JobTab.PROFILE -> {
                    it.copy(
                        currentJobFilter = it.profileFilter
                    )
                }
            }
        }
    }

    fun updateJobFilter(jobFilter: JobFilter) {
        viewModelState.update {
            when (jobFilter) {
                is JobFilter.JobOpenings -> {
                    it.copy(
                        jobOpeningsFilter = jobFilter,
                        currentJobFilter = jobFilter
                    )
                }
                is JobFilter.Profile -> {
                    it.copy(
                        profileFilter = jobFilter,
                        currentJobFilter = jobFilter
                    )
                }
            }
        }
    }
}

private data class JobScreenViewModelState(
    val userJob: Job = Job.ACTOR,
    val currentJobFilter: JobFilter = JobFilter.JobOpenings(FilterType.Recent),
    val jobOpeningsFilter: JobFilter = JobFilter.JobOpenings(FilterType.Recent),
    val profileFilter: JobFilter = JobFilter.Profile(FilterType.Recent),
) {
    fun toUiState(): JobScreenUiState = JobScreenUiState(
        type = when (userJob) {
            Job.STAFF -> Type.STAFF
            else -> Type.ACTOR
        },
        currentJobFilter = currentJobFilter,
        jobOpeningsFilter = jobOpeningsFilter,
        profileFilter = profileFilter
    )
}

data class JobScreenUiState(
    val type: Type,
    val currentJobFilter: JobFilter,
    val jobOpeningsFilter: JobFilter,
    val profileFilter: JobFilter
)

sealed interface JobFilter {
    val currentFilterType: FilterType

    data class JobOpenings(
        override val currentFilterType: FilterType
    ) : JobFilter

    data class Profile(
        override val currentFilterType: FilterType
    ) : JobFilter
}

enum class FilterType(@StringRes val titleRes: Int) {
    Recent(R.string.job_tab_main_filter_recent),
    View(R.string.job_tab_main_filter_lookups),
    Deadline(R.string.job_tab_main_filter_due)
}