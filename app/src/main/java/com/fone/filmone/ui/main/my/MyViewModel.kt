package com.fone.filmone.ui.main.my

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.response.user.UserResponse
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetUserInfoUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    init {
        viewModelScope.launch {
            getUserInfoUseCase()
                .onSuccess { response ->
                    viewModelState.update {
                        it.copy(userResponse = response)
                    }
                }
        }
    }
}

private data class MyViewModelState(
    val userResponse: UserResponse? = null
) {
    fun toUiState() = MyUiState(
        nickname = userResponse?.user?.nickname ?: ""
    )
}

data class MyUiState(
    val nickname: String
)