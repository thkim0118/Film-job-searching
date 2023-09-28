package com.fone.filmone.ui.profile.detail.staff

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
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
class StaffProfileDetailViewModel @Inject constructor(
    private val getProfileDetailUseCase: GetProfileDetailUseCase,
    private val wantProfileUseCase: WantProfileUseCase,
    stateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(StaffProfileDetailViewModelState())

    val uiState = viewModelState
        .map(StaffProfileDetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            val profileId =
                stateHandle.get<Int>(FOneDestinations.StaffProfileDetail.argProfileId)
                    ?: return@launch

            getProfileDetailUseCase(
                profileId = profileId,
                type = Type.STAFF
            ).onSuccess { profileDetailResponse ->
                if (profileDetailResponse == null) {
                    showToast(R.string.toast_empty_data)
                    return@onSuccess
                }

                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val content = profileDetailResponse.profile

                viewModelState.update {
                    it.copy(
                        staffProfileDetailUiModel = StaffProfileDetailUiModel(
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
                            careerDetail = content.careerDetail,
                            categories = content.categories,
                            isWant = content.isWant,
                        )
                    )
                }
            }.onFail {
                showToast(it.message)
            }
        }
    }

    fun contact() {
        showToast(R.string.service)
    }

    fun wantProfile() = viewModelScope.launch {
        val profileId: Long = uiState.value.profileId
        wantProfileUseCase(profileId)
            .onSuccess {
                viewModelState.update { state ->
                    state.copy(
                        staffProfileDetailUiModel = state.staffProfileDetailUiModel.copy(
                            isWant = state.staffProfileDetailUiModel.isWant.not()
                        )
                    )
                }
            }.onFail {
            }
    }
}

private data class StaffProfileDetailViewModelState(
    val staffProfileDetailUiModel: StaffProfileDetailUiModel = StaffProfileDetailUiModel(
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
        careerDetail = "",
        categories = emptyList(),
        isWant = false,
    ),
) {
    fun toUiState(): StaffProfileDetailUiModel = staffProfileDetailUiModel
}

data class StaffProfileDetailUiModel(
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
    val careerDetail: String,
    val categories: List<Category>,
    val isWant: Boolean,
)
