package com.fone.filmone.ui.profile.detail.actor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.response.profiles.detail.ProfileDetailResponse
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetProfileDetailUseCase
import com.fone.filmone.domain.usecase.WantProfileUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ActorProfileDetailViewModel @Inject constructor(
    private val getProfileDetailUseCase: GetProfileDetailUseCase,
    private val wantProfileUseCase: WantProfileUseCase,
    stateHandle: SavedStateHandle
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(ActorProfileDetailViewModelState())

    val uiState = viewModelState
        .map(ActorProfileDetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun contact() {
        showToast(R.string.service)
    }

    fun wantProfile() = viewModelScope.launch {
        val profileId: Long = uiState.value.profileId
        wantProfileUseCase(profileId)
            .onSuccess {
                viewModelState.update {
                    it.copy(
                        it.profileDetailResponse?.copy(
                            profile = it.profileDetailResponse.profile.copy(
                                isWant = it.profileDetailResponse.profile.isWant.not()
                            )
                        )
                    )
                }
            }.onFail {
            }
    }

    init {
        viewModelScope.launch {
            val profileId =
                stateHandle.get<Int>(FOneDestinations.ActorProfileDetail.argActorProfileDetail)
                    ?: return@launch

            getProfileDetailUseCase(
                profileId = profileId,
                type = Type.ACTOR
            ).onSuccess { profileResponse ->
                if (profileResponse == null) {
                    showToast(R.string.toast_empty_data)
                    return@onSuccess
                }

                viewModelState.update {
                    it.copy(profileDetailResponse = profileResponse)
                }
            }.onFail {
                showToast(it.message)
            }
        }
    }
}

private data class ActorProfileDetailViewModelState(
    val profileDetailResponse: ProfileDetailResponse? = null
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    fun toUiState(): ActorProfileDetailUiState = if (profileDetailResponse != null) {
        val content = profileDetailResponse.profile
        ActorProfileDetailUiState(
            profileId = content.id.toLong(),
            date = dateFormat.format(content.createdAt),
            viewCount = String.format("%,d", content.viewCount),
            profileImageUrl = content.profileUrl,
            userNickname = content.userNickname,
            userType = content.type.name,
            articleTitle = content.hookingComment.split('\n').firstOrNull() ?: "",
            profileImageUrls = content.profileUrls,
            userName = content.name,
            gender = content.gender,
            birthday = content.birthday,
            heightWeight = "${content.height}cm | ${content.weight}kg",
            email = content.email,
            specialty = content.specialty,
            sns = content.sns,
            detail = content.details,
            mainCareer = content.hookingComment,
            categories = content.categories,
            isWant = content.isWant,
        )
    } else {
        ActorProfileDetailUiState(
            profileId = 0L,
            date = "",
            viewCount = "",
            profileImageUrl = "",
            userNickname = "",
            userType = "",
            articleTitle = "",
            profileImageUrls = emptyList(),
            userName = "",
            gender = Gender.IRRELEVANT,
            birthday = "",
            heightWeight = "",
            email = "",
            specialty = "",
            sns = "",
            detail = "",
            mainCareer = "",
            categories = emptyList(),
            isWant = false
        )
    }
}

data class ActorProfileDetailUiState(
    val profileId: Long,
    val date: String,
    val viewCount: String,
    val profileImageUrl: String,
    val userNickname: String,
    val userType: String,
    val articleTitle: String,
    val profileImageUrls: List<String>,
    val userName: String,
    val gender: Gender,
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
