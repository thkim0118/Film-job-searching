package com.fone.filmone.ui.myinfo

import androidx.lifecycle.viewModelScope
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.data.datamodel.response.user.Interests
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.domain.usecase.GetUserInfoUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MyInfoUiState())
    val uiState: StateFlow<MyInfoUiState> = _uiState.asStateFlow()

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        getUserInfoUseCase()
            .onSuccess { userResponse ->
                val user = userResponse.user
                _uiState.update {
                    it.copy(
                        profileUrl = user.profileUrl,
                        nickname = user.nickname,
                        job = user.job,
                        interests = user.interests
                    )
                }
            }.onFail {
                // TODO Error 처리
            }
    }

}

data class MyInfoUiState(
    val profileUrl: String? = null,
    val nickname: String = "",
    val job: Job? = null,
    val interests: List<Interests> = emptyList()
)