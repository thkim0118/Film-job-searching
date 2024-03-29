package com.fone.filmone.ui.main

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetUserInfoUseCase
import com.fone.filmone.domain.usecase.LogoutUseCase
import com.fone.filmone.domain.usecase.SignOutUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.main.job.JobFilterType
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel() {

    private val viewModelState = MutableStateFlow(MainViewModelState())

    val uiState = viewModelState
        .map(MainViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            viewModelState.value.toUiState()
        )

    init {
        initUserInfo()
    }

    private fun initUserInfo() = viewModelScope.launch {
        getUserInfoUseCase()
            .onSuccess { userResponse ->
                if (userResponse == null) {
                    return@onSuccess
                }

                viewModelState.update {
                    it.copy(
                        type = when (userResponse.user.job) {
                            Job.STAFF -> Type.STAFF
                            else -> Type.ACTOR
                        }
                    )
                }
            }
    }

    suspend fun logout() = viewModelScope.launch {
        logoutUseCase()
            .onSuccess {
                FOneNavigator.navigateTo(
                    navDestinationState = NavDestinationState(
                        route = FOneDestinations.Login.route,
                        isPopAll = true
                    )
                )
            }.onFail {
                showToast(R.string.toast_empty_data)
            }
    }

    suspend fun signOut() = viewModelScope.launch {
        signOutUseCase()
            .onSuccess {
                viewModelState.update {
                    it.copy(
                        mainDialogState = MainDialogState.WithdrawalComplete
                    )
                }
            }.onFail {
                showToast(R.string.toast_empty_data)
            }
    }

    fun clearDialog() {
        viewModelState.update {
            it.copy(mainDialogState = MainDialogState.Clear)
        }
    }

    fun showFloatingDimBackground() {
        viewModelState.update {
            it.copy(isFloatingClick = true)
        }
    }

    fun hideFloatingDimBackground() {
        viewModelState.update {
            it.copy(isFloatingClick = false)
        }
    }

    fun updateTypeAsActor() {
        viewModelState.update {
            it.copy(
                type = Type.ACTOR
            )
        }
    }

    fun updateJobOpeningFilter(jobFilterType: JobFilterType) {
        viewModelState.update {
            when (it.type) {
                Type.STAFF -> {
                    it.copy(
                        staffJobOpeningsSorting = JobSorting.JobOpenings(jobFilterType),
                        currentJobOpeningSorting = JobSorting.JobOpenings(jobFilterType)
                    )
                }

                else -> {
                    it.copy(
                        actorJobOpeningsSorting = JobSorting.JobOpenings(jobFilterType),
                        currentJobOpeningSorting = JobSorting.JobOpenings(jobFilterType)
                    )
                }
            }
        }
    }

    fun updateProfileFilter(jobFilterType: JobFilterType) {
        viewModelState.update {
            when (it.type) {
                Type.STAFF -> {
                    it.copy(
                        staffJobOpeningsSorting = JobSorting.Profile(jobFilterType),
                        currentProfileSorting = JobSorting.Profile(jobFilterType)
                    )
                }

                else -> {
                    it.copy(
                        actorJobOpeningsSorting = JobSorting.Profile(jobFilterType),
                        currentProfileSorting = JobSorting.Profile(jobFilterType)
                    )
                }
            }
        }
    }

    fun updateJobTabUserType(type: Type) {
        viewModelState.update {
            it.copy(type = type)
        }
    }
}

private data class MainViewModelState(
    val mainDialogState: MainDialogState = MainDialogState.Clear,
    val isFloatingClick: Boolean = false,
    val type: Type = Type.ACTOR,
    val currentJobOpeningSorting: JobSorting = JobSorting.JobOpenings(JobFilterType.Recent),
    val currentProfileSorting: JobSorting = JobSorting.Profile(JobFilterType.Recent),
    val actorJobOpeningsSorting: JobSorting = JobSorting.JobOpenings(JobFilterType.Recent),
    val actorProfileSorting: JobSorting = JobSorting.Profile(JobFilterType.Recent),
    val staffJobOpeningsSorting: JobSorting = JobSorting.JobOpenings(JobFilterType.Recent),
    val staffProfileSorting: JobSorting = JobSorting.Profile(JobFilterType.Recent),
) {
    fun toUiState(): MainUiState {
        return MainUiState(
            type = type,
            mainDialogState = mainDialogState,
            isFloatingClick = isFloatingClick,
            currentJobOpeningSorting = currentJobOpeningSorting,
            actorJobOpeningsSorting = actorJobOpeningsSorting,
            actorProfileSorting = actorProfileSorting,
            currentProfileSorting = currentProfileSorting,
        )
    }
}

data class MainUiState(
    val mainDialogState: MainDialogState,
    val isFloatingClick: Boolean,
    val type: Type,
    val currentJobOpeningSorting: JobSorting,
    val currentProfileSorting: JobSorting,
    val actorJobOpeningsSorting: JobSorting,
    val actorProfileSorting: JobSorting,
)

sealed interface MainDialogState {
    object Clear : MainDialogState
    object WithdrawalComplete : MainDialogState
}

sealed interface JobSorting {
    val currentJobFilterType: JobFilterType

    data class JobOpenings(
        override val currentJobFilterType: JobFilterType
    ) : JobSorting

    data class Profile(
        override val currentJobFilterType: JobFilterType
    ) : JobSorting
}
