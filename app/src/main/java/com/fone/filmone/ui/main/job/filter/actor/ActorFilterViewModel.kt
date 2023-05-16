package com.fone.filmone.ui.main.job.filter.actor

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ActorFilterViewModel @Inject constructor(
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(ActorFilterViewModelState())

    val uiState = viewModelState
        .map(ActorFilterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun updateGenderSelectAll() {
        viewModelState.update { state ->
            state.copy(
                genders = if (state.genders.size == Gender.values().size) {
                    emptySet()
                } else {
                    Gender.values().toSet()
                }
            )
        }
    }

    fun updateInterestSelectAll() {
        viewModelState.update { state ->
            state.copy(
                interests = if (state.interests.size == Category.values().size) {
                    emptySet()
                } else {
                    Category.values().toSet()
                }
            )
        }
    }

    fun updateGender(gender: Gender, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                genders = if (enable) {
                    state.genders + setOf(gender)
                } else {
                    state.genders.filterNot { it == gender }.toSet()
                }
            )
        }
    }

    fun updateInterest(interest: Category, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                interests = if (enable) {
                    state.interests + setOf(interest)
                } else {
                    state.interests.filterNot { it == interest }.toSet()
                }
            )
        }
    }

    fun updateAgeRangeReset() {
        viewModelState.update { state ->
            state.copy(ageRange = 1f..70f)
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(ageRange = ageRange)
        }
    }
}

private data class ActorFilterViewModelState(
    val genders: Set<Gender> = emptySet(),
    val interests: Set<Category> = emptySet(),
    val ageRange: ClosedFloatingPointRange<Float> = 1f..70f,
) {
    fun toUiState(): ActorFilterUiState = ActorFilterUiState(
        genders = genders,
        interests = interests,
        ageRange = ageRange
    )
}

data class ActorFilterUiState(
    val genders: Set<Gender>,
    val interests: Set<Category>,
    val ageRange: ClosedFloatingPointRange<Float>
)