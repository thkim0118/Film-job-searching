package com.fone.filmone.ui.signup

import androidx.lifecycle.ViewModel
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.ui.signup.model.SignUpVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpFirstViewModel @Inject constructor() : ViewModel() {
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

    fun updateSavedSignupVo(signUpVo: SignUpVo) {
        _uiState.update { uiState ->
            uiState.copy(
                job = Job.values().find { job ->
                    job.name == signUpVo.job
                },
                interests = signUpVo.interests.map { interest ->
                    Category.values().find { category ->
                        category.name == interest
                    } ?: Category.ETC
                }.toList()
            )
        }
    }
}

data class SignUpFirstUiState(
    val job: Job?,
    val interests: List<Category>
)
