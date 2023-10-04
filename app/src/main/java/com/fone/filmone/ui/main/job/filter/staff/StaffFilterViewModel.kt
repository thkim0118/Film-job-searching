package com.fone.filmone.ui.main.job.filter.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StaffFilterViewModel @Inject constructor() : ViewModel() {
    private val viewModelState = MutableStateFlow(StaffFilterViewModelState())

    val uiState = viewModelState
        .map(StaffFilterViewModelState::toUiState)
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

    fun updateDomainSelectAll() {
        viewModelState.update { state ->
            state.copy(
                domains = if (state.domains.size == Domain.values().size) {
                    emptySet()
                } else {
                    Domain.values().toSet()
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
            state.copy(ageRange = 0f..70f)
        }
    }

    fun updateAgeRange(ageRange: ClosedFloatingPointRange<Float>) {
        viewModelState.update { state ->
            state.copy(ageRange = ageRange)
        }
    }

    fun updateDomain(domain: Domain, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                domains = if (enable) {
                    state.domains + setOf(domain)
                } else {
                    state.domains.filterNot { it == domain }.toSet()
                }
            )
        }
    }

    fun unSelectAllGenders() {
        viewModelState.update { state ->
            state.copy(genders = emptySet())
        }
    }

    fun unSelectAllInterests() {
        viewModelState.update { state ->
            state.copy(interests = emptySet())
        }
    }

    fun unSelectAllDomains() {
        viewModelState.update { state ->
            state.copy(domains = emptySet())
        }
    }
}

private data class StaffFilterViewModelState(
    val genders: Set<Gender> = emptySet(),
    val interests: Set<Category> = emptySet(),
    val ageRange: ClosedFloatingPointRange<Float> = 0f..70f,
    val domains: Set<Domain> = emptySet()
) {
    fun toUiState(): StaffFilterUiState = StaffFilterUiState(
        genders = genders,
        interests = interests,
        ageRange = ageRange,
        domains = domains
    )
}

data class StaffFilterUiState(
    val genders: Set<Gender>,
    val interests: Set<Category>,
    val ageRange: ClosedFloatingPointRange<Float>,
    val domains: Set<Domain> = emptySet()
)
