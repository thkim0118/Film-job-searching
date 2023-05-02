package com.fone.filmone.ui.favorite

import androidx.lifecycle.viewModelScope
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.usecase.GetFavoriteProfilesActorUseCase
import com.fone.filmone.domain.usecase.GetFavoriteProfilesStaffUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteProfilesActorUseCase: GetFavoriteProfilesActorUseCase,
    private val getFavoriteProfilesStaffUseCase: GetFavoriteProfilesStaffUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(FavoriteViewModelState())

    val fakeActorModels = listOf(
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "정용식",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "정용식",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "정용식",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "정용식",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "정용식",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "정용식",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "정용식",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "정용식",
            info = "1985년생 (38살)"
        ),
    )

    val fakeStaffModels = listOf(
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "황우슬혜",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "황우슬혜",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "황우슬혜",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "황우슬혜",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "황우슬혜",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "황우슬혜",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "황우슬혜",
            info = "1985년생 (38살)"
        ),
        FavoriteUiModel(
            profileUrl = "https://picsum.photos/200",
            name = "황우슬혜",
            info = "1985년생 (38살)"
        ),
    )

    val uiState = viewModelState
        .map(FavoriteViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            val actorResponse = flowOf(getFavoriteProfilesActorUseCase(page = 1).getOrNull())
            val staffResponse = flowOf(getFavoriteProfilesStaffUseCase(page = 1).getOrNull())

            combine(actorResponse, staffResponse) { actorResult, staffResult ->
                FavoriteViewModelState(
                    actorProfiles = actorResult?.profiles?.content?.map { content ->
                        FavoriteUiModel(
                            profileUrl = content.profileUrl,
                            name = content.name,
                            info = content.birthday + content.age
                        )
                    } ?: emptyList(),
                    staffProfiles = staffResult?.profiles?.content?.map { content ->
                        FavoriteUiModel(
                            profileUrl = content.profileUrl,
                            name = content.name,
                            info = content.birthday + content.age
                        )
                    } ?: emptyList()
                )
            }.onEach { combinedViewModelState ->
                viewModelState.update { combinedViewModelState }
            }.launchIn(viewModelScope)
        }

//        viewModelState.update {
//            it.copy(
//                actorProfiles = fakeActorModels,
////                staffProfiles = fakeStaffModels,
//                staffProfiles = emptyList()
//            )
//        }
    }
}

private data class FavoriteViewModelState(
    val actorProfiles: List<FavoriteUiModel> = emptyList(),
    val staffProfiles: List<FavoriteUiModel> = emptyList()
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
    val staffUiState: ProfileUiState
)

data class FavoriteUiModel(
    val profileUrl: String,
    val name: String,
    val info: String
)

sealed interface ProfileUiState {
    object NoData : ProfileUiState
    data class HasItems(val uiModels: List<FavoriteUiModel>) : ProfileUiState
}