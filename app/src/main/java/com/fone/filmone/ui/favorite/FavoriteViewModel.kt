package com.fone.filmone.ui.favorite

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetFavoriteProfilesActorUseCase
import com.fone.filmone.domain.usecase.GetFavoriteProfilesStaffUseCase
import com.fone.filmone.domain.usecase.WantProfileUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteProfilesActorUseCase: GetFavoriteProfilesActorUseCase,
    private val getFavoriteProfilesStaffUseCase: GetFavoriteProfilesStaffUseCase,
    private val wantProfileUseCase: WantProfileUseCase,
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(FavoriteViewModelState())

    val uiState = viewModelState
        .map(FavoriteViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            val actorResponse = flowOf(getFavoriteProfilesActorUseCase(page = 0).getOrNull())
            val staffResponse = flowOf(getFavoriteProfilesStaffUseCase(page = 0).getOrNull())

            combine(actorResponse, staffResponse) { actorResult, staffResult ->
                FavoriteViewModelState(
                    actorProfiles = actorResult?.profiles?.content?.map { content ->
                        FavoriteUiModel(
                            id = content.id,
                            profileUrl = content.profileUrl,
                            name = content.name,
                            info = "${content.birthday.slice(0..3)}년생 (${content.age}살)",
                            isWant = content.isWant,
                        )
                    } ?: emptyList(),
                    staffProfiles = staffResult?.profiles?.content?.map { content ->
                        FavoriteUiModel(
                            id = content.id,
                            profileUrl = content.profileUrl,
                            name = content.name,
                            info = "${content.birthday.slice(0..3)}년생 (${content.age}살)",
                            isWant = content.isWant,
                        )
                    } ?: emptyList()
                )
            }.onEach { combinedViewModelState ->
                viewModelState.update { combinedViewModelState }
            }.launchIn(viewModelScope)
        }
    }

    fun wantProfile(profileId: Int, type: Type) = viewModelScope.launch {
        wantProfileUseCase(profileId = profileId.toLong())
            .onSuccess {
                viewModelState.update { state ->
                    when (type) {
                        Type.ACTOR -> {
                            state.copy(
                                actorProfiles = state.actorProfiles.map { uiModel ->
                                    if (uiModel.id == profileId) {
                                        uiModel.copy(isWant = uiModel.isWant.not())
                                    } else {
                                        uiModel
                                    }
                                }
                            )
                        }

                        Type.STAFF -> {
                            state.copy(
                                staffProfiles = state.staffProfiles.map { uiModel ->
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
            }
    }
}

private data class FavoriteViewModelState(
    val actorProfiles: List<FavoriteUiModel> = emptyList(),
    val staffProfiles: List<FavoriteUiModel> = emptyList(),
) {
    fun toUiState(): FavoriteUiState {
        val actorUiState = if (actorProfiles.isEmpty()) {
            ProfileUiState.NoData
        } else {
            ProfileUiState.HasItems(actorProfiles)
        }
        val staffProfiles = if (staffProfiles.isEmpty()) {
            ProfileUiState.NoData
        } else {
            ProfileUiState.HasItems(staffProfiles)
        }

        return FavoriteUiState(actorUiState, staffProfiles)
    }
}

data class FavoriteUiState(
    val actorUiState: ProfileUiState,
    val staffUiState: ProfileUiState,
)

data class FavoriteUiModel(
    val id: Int,
    val profileUrl: String,
    val name: String,
    val info: String,
    val isWant: Boolean,
)

sealed interface ProfileUiState {
    object NoData : ProfileUiState
    data class HasItems(val uiModels: List<FavoriteUiModel>) : ProfileUiState
}
