package com.fone.filmone.ui.signup

import androidx.lifecycle.ViewModel
import com.fone.filmone.data.datamodel.response.common.user.Category
import com.fone.filmone.data.datamodel.response.user.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpFirstViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpFirstUiState(null, emptyList()))
    val uiState: StateFlow<SignUpFirstUiState> = _uiState.asStateFlow()

    fun updateJobTag(job: Job) {
        _uiState.update { uiState ->
            uiState.copy(job = job)
        }
    }

    fun updateInterest(interest: Category, enable: Boolean) {
        _uiState.update { uiState ->
            uiState.copy(
                interests = if (enable) {
                    uiState.interests + listOf(interest)
                } else {
                    uiState.interests.filterNot { it == interest }
                }
            )
        }
    }
}

data class SignUpFirstUiState(
    val job: Job?,
    val interests: List<Category>
)