package com.fone.filmone.ui.myregister

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.response.common.user.Category
import com.fone.filmone.data.datamodel.response.common.user.Gender
import com.fone.filmone.data.datamodel.response.jobopenings.Type
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MyRegisterViewModel @Inject constructor(
) : BaseViewModel() {

    private val myRegisterViewModelState = MutableStateFlow(MyRegisterViewModelState())

    val uiState = myRegisterViewModelState
        .map(MyRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            myRegisterViewModelState.value.toUiState()
        )

    private val _dialogState = MutableStateFlow<MyRegisterDialogState>(MyRegisterDialogState.Clear)
    val dialogState: StateFlow<MyRegisterDialogState> = _dialogState.asStateFlow()
}

private data class MyRegisterViewModelState(
    val registerPosts: List<RegisterPostUiModel> = emptyList(),
    val profilePosts: List<RegisterPostProfileUiModel> = emptyList()
) {
    fun toUiState(): MyRegisterUiState = MyRegisterUiState(registerPosts, profilePosts)
}

data class MyRegisterUiState(
    val registerPosts: List<RegisterPostUiModel>,
    val profilePosts: List<RegisterPostProfileUiModel>
)

data class RegisterPostUiModel(
    val type: Type,
    val categories: List<Category>,
    val title: String,
    val deadline: String,
    val director: String,
    val gender: Gender,
    val period: String,
    val jobType: JobType,
    val casting: String,
)

data class RegisterPostProfileUiModel(
    val profileUrl: String,
    val name: String,
    val type: Type,
    val info: String
)

sealed interface MyRegisterDialogState {
    object Clear : MyRegisterDialogState
    object RegisterPostDelete : MyRegisterDialogState
}