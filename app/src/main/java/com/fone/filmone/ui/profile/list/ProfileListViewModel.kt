package com.fone.filmone.ui.profile.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.profile.list.model.ProfileListArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileListViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(ProfileListViewModelState())

    val uiState = viewModelState
        .map(ProfileListViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        val profileListArguments: ProfileListArguments =
            ProfileListArguments.fromJson(
                stateHandle.get<String>(FOneDestinations.ProfileList.argProfileList) ?: ""
            )

        viewModelState.update {
            it.copy(
                userName = profileListArguments.userName,
                profileUrls = profileListArguments.profileUrls
            )
        }
    }
}

private data class ProfileListViewModelState(
    val userName: String = "",
    val profileUrls: List<String> = emptyList()
) {
    fun toUiState(): ProfileListUiState {
        return ProfileListUiState(
            userName = userName,
            profileUrls = profileUrls
        )
    }
}

data class ProfileListUiState(
    val userName: String,
    val profileUrls: List<String>
)
