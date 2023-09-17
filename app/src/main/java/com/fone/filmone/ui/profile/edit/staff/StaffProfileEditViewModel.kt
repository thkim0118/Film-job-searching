package com.fone.filmone.ui.profile.edit.staff

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.data.datamodel.request.profile.ProfileRegisterRequest
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetProfileDetailUseCase
import com.fone.filmone.domain.usecase.ModifyProfileUseCase
import com.fone.filmone.domain.usecase.UploadImageUseCase
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.profile.common.staff.StaffProfileViewModel
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileFocusEvent
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileUiEvent
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffProfileEditViewModel @Inject constructor(
    private val modifyProfileUseCase: ModifyProfileUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getProfileDetailUseCase: GetProfileDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : StaffProfileViewModel() {
    override val viewModelState: MutableStateFlow<StaffProfileViewModelState> =
        MutableStateFlow(StaffProfileEditViewModelState())

    override val uiState = viewModelState
        .map(StaffProfileViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    val profileId: Int?

    init {
        profileId = getContentId(savedStateHandle = savedStateHandle)

        fetchInitialContent()
    }

    private fun getContentId(savedStateHandle: SavedStateHandle) =
        savedStateHandle.get<Int>(FOneDestinations.StaffProfileEdit.argContentId)

    private fun fetchInitialContent() = viewModelScope.launch {
        if (profileId != null && profileId > 0) {
            getProfileDetailUseCase(profileId, Type.STAFF)
                .onSuccess { response ->
                    val profileContent = response?.profile ?: return@onSuccess

                    viewModelState.update {
                        it.copy(
//                            pictureList = it.pictureList,
                            name = profileContent.name,
                            hookingComments = profileContent.hookingComment,
                            birthday = profileContent.birthday,
                            gender = profileContent.gender,
                            genderTagEnable = profileContent.gender == Gender.IRRELEVANT,
                            domains = profileContent.domains.toSet(),
                            specialty = profileContent.specialty,
                            sns = profileContent.sns,
                            detailInfo = profileContent.details,
                            career = profileContent.career,
                            categories = profileContent.categories.toSet(),
                            categoryTagEnable = profileContent.categories.size == Category.values().size,
                        )
                    }
                }.onFail {
                    showToast(it.message)
                }
        }
    }

    override fun uploadProfileImages() = viewModelScope.launch {
        uploadImageUseCase(
            uiState.value.pictureEncodedDataList.map {
                UploadingImage(
                    imageData = it,
                    resource = UploadImageUseCase.userProfileResource,
                    stageVariables = UploadImageUseCase.stageVariables
                )
            }
        ).onSuccess { response ->
            if (response == null) {
                return@onSuccess
            }

            register(response.map { it.imageUrl })
        }.onFail {
            showToast(it.message)
        }
    }

    override fun register(imageUrls: List<String>) = viewModelScope.launch {
        if (profileId != null && profileId > 0) {
            modifyProfileUseCase(
                profileId = profileId,
                profileRegisterRequest = ProfileRegisterRequest(
                    profileUrl = imageUrls.firstOrNull() ?: "",
                    profileUrls = imageUrls,
                    name = uiState.value.name,
                    hookingComment = uiState.value.hookingComments,
                    birthday = uiState.value.birthday,
                    gender = uiState.value.gender?.name ?: Gender.IRRELEVANT.name,
                    domains = uiState.value.domains.map { it.name },
                    email = uiState.value.email,
                    specialty = uiState.value.specialty,
                    sns = uiState.value.sns,
                    details = uiState.value.detailInfo,
                    career = uiState.value.career?.name ?: Career.IRRELEVANT.name,
                    categories = uiState.value.categories.map { it.name },
                    type = Type.STAFF.name,
                    height = null,
                    weight = null
                )
            ).onSuccess { response ->
                if (response == null) {
                    showToast("response is null")
                    return@onSuccess
                }

                viewModelState.update {
                    it.copy(staffProfileUiEvent = StaffProfileUiEvent.RegisterComplete)
                }
            }
        }
    }
}

private class StaffProfileEditViewModelState(
    override val pictureEncodedDataList: List<String> = emptyList(),
    override val name: String = "",
    override val hookingComments: String = "",
    override val commentsTextLimit: Int = 50,
    override val birthday: String = "",
    override val gender: Gender? = Gender.IRRELEVANT,
    override val genderTagEnable: Boolean = false,
    override val domains: Set<Domain> = emptySet(),
    override val email: String = "",
    override val specialty: String = "",
    override val sns: String = "",
    override val detailInfo: String = "",
    override val detailInfoTextLimit: Int = 200,
    override val career: Career? = null,
    override val careerDetail: String = "",
    override val careerDetailTextLimit: Int = 500,
    override val categories: Set<Category> = emptySet(),
    override val categoryTagEnable: Boolean = false,
    override val registerButtonEnable: Boolean = false,
    override val staffProfileUiEvent: StaffProfileUiEvent = StaffProfileUiEvent.Clear,
    override val focusEvent: StaffProfileFocusEvent? = null,
) : StaffProfileViewModelState() {
    override fun copy(
        pictureEncodedDataList: List<String>,
        name: String,
        hookingComments: String,
        commentsTextLimit: Int,
        birthday: String,
        gender: Gender?,
        genderTagEnable: Boolean,
        domains: Set<Domain>,
        email: String,
        specialty: String,
        sns: String,
        detailInfo: String,
        detailInfoTextLimit: Int,
        career: Career?,
        careerDetail: String,
        careerDetailTextLimit: Int,
        categories: Set<Category>,
        categoryTagEnable: Boolean,
        registerButtonEnable: Boolean,
        staffProfileUiEvent: StaffProfileUiEvent,
        focusEvent: StaffProfileFocusEvent?,
    ): StaffProfileViewModelState = StaffProfileEditViewModelState(
        pictureEncodedDataList = pictureEncodedDataList,
        name = name,
        hookingComments = hookingComments,
        commentsTextLimit = commentsTextLimit,
        birthday = birthday,
        gender = gender,
        genderTagEnable = genderTagEnable,
        domains = domains,
        email = email,
        specialty = specialty,
        sns = sns,
        detailInfo = detailInfo,
        detailInfoTextLimit = detailInfoTextLimit,
        career = career,
        careerDetail = careerDetail,
        careerDetailTextLimit = careerDetailTextLimit,
        categories = categories,
        categoryTagEnable = categoryTagEnable,
        registerButtonEnable = registerButtonEnable,
        staffProfileUiEvent = staffProfileUiEvent,
        focusEvent = focusEvent,
    )
}
