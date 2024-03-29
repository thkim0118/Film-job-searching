package com.fone.filmone.ui.main.my

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.data.datamodel.response.user.UserResponse
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetUserInfoUseCase
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
class MyViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(MyViewModelState())

    val uiState = viewModelState
        .map(MyViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun fetchUserInfo() = viewModelScope.launch {
        getUserInfoUseCase()
            .onSuccess { response ->
                viewModelState.update {
                    it.copy(userResponse = response)
                }
            }
    }
}

private data class MyViewModelState(
    val userResponse: UserResponse? = null
) {
    fun toUiState() = MyUiState(
        nickname = userResponse?.user?.nickname ?: "",
        profileUrl = userResponse?.user?.profileUrl ?: "",
        job = userResponse?.user?.job ?: Job.NORMAL
    )
}

data class MyUiState(
    val nickname: String,
    val profileUrl: String,
    val job: Job
)
