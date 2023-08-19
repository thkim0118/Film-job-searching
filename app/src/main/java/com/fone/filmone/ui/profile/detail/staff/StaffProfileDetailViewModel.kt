package com.fone.filmone.ui.profile.detail.staff

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.response.profiles.detail.ProfileDetailResponse
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetProfileDetailUseCase
import com.fone.filmone.domain.usecase.WantProfileUseCase
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
class StaffProfileDetailViewModel @Inject constructor(
    private val getProfileDetailUseCase: GetProfileDetailUseCase,
    private val wantProfileUseCase: WantProfileUseCase,
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(StaffProfileDetailViewModelState())

    val uiState = viewModelState
        .map(StaffProfileDetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun wantProfile() = viewModelScope.launch {
        val profileId: Long = uiState.value.profileId
        wantProfileUseCase(profileId)
            .onSuccess {
                viewModelState.update {
                    it.copy(
                        profileDetailResponse = it.profileDetailResponse?.copy(
                            profile = it.profileDetailResponse.profile.copy(
                                isWant = it.profileDetailResponse.profile.isWant.not()
                            )
                        )
                    )
                }
            }.onFail {
            }
    }
}

private data class StaffProfileDetailViewModelState(
    val profileDetailResponse: ProfileDetailResponse? = null
) {
    fun toUiState(): StaffProfileDetailUiState = if (profileDetailResponse != null) {
        StaffProfileDetailUiState(
            profileId = profileDetailResponse.profile.id.toLong(),
            date = "",
            viewCount = String.format("%,d", profileDetailResponse.profile.viewCount),
            profileImageUrl = profileDetailResponse.profile.profileUrl,
            userNickname = "",
            userType = "",
            articleTitle = "",
            profileImageUrls = profileDetailResponse.profile.profileUrls,
            userName = profileDetailResponse.profile.name,
            gender = profileDetailResponse.profile.gender.name,
            birthday = profileDetailResponse.profile.birthday,
            heightWeight = "${profileDetailResponse.profile.height}cm | ${profileDetailResponse.profile.weight}kg",
            email = profileDetailResponse.profile.email,
            specialty = profileDetailResponse.profile.specialty,
            sns = profileDetailResponse.profile.sns,
            detail = profileDetailResponse.profile.details,
            mainCareer = profileDetailResponse.profile.hookingComment,
            categories = profileDetailResponse.profile.categories,
            isWant = profileDetailResponse.profile.isWant,
        )
    } else {
        StaffProfileDetailUiState(
            profileId = 0L,
            date = "",
            viewCount = "",
            profileImageUrl = "",
            userNickname = "",
            userType = "",
            articleTitle = "",
            profileImageUrls = emptyList(),
            userName = "",
            gender = "",
            birthday = "",
            heightWeight = "",
            email = "",
            specialty = "",
            sns = "",
            detail = "",
            mainCareer = "",
            categories = emptyList(),
            isWant = false,
        )
    }
}

data class StaffProfileDetailUiState(
    val profileId: Long,
    val date: String,
    val viewCount: String,
    val profileImageUrl: String,
    val userNickname: String,
    val userType: String,
    val articleTitle: String,
    val profileImageUrls: List<String>,
    val userName: String,
    val gender: String,
    val birthday: String,
    val heightWeight: String,
    val email: String,
    val specialty: String,
    val sns: String,
    val detail: String,
    val mainCareer: String,
    val categories: List<Category>,
    val isWant: Boolean,
)
