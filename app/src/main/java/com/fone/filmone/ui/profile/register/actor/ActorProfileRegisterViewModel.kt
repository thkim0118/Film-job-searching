package com.fone.filmone.ui.profile.register.actor

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.data.datamodel.request.profile.ProfileRegisterRequest
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.RegisterProfileUseCase
import com.fone.filmone.domain.usecase.UploadImageUseCase
import com.fone.filmone.ui.profile.common.actor.ActorProfileViewModel
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileFocusEvent
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileUiEvent
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorProfileRegisterViewModel @Inject constructor(
    private val registerProfileUseCase: RegisterProfileUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
) : ActorProfileViewModel() {

    override val viewModelState: MutableStateFlow<ActorProfileViewModelState> =
        MutableStateFlow(ActorProfileRegisterViewModelState())

    override val uiState = viewModelState
        .map(ActorProfileViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    override fun register(imageUrls: List<String>) = viewModelScope.launch {
        registerProfileUseCase(
            profileRegisterRequest = ProfileRegisterRequest(
                birthday = uiState.value.birthday,
                career = uiState.value.career?.name ?: Career.IRRELEVANT.name,
                categories = uiState.value.categories.map { it.name },
                details = uiState.value.detailInfo,
                domains = null,
                email = uiState.value.email,
                gender = uiState.value.gender?.name ?: Gender.IRRELEVANT.name,
                height = uiState.value.height.toInt(),
                hookingComment = uiState.value.hookingComments,
                name = uiState.value.name,
                profileUrl = imageUrls.firstOrNull() ?: "",
                profileUrls = imageUrls,
                sns = uiState.value.sns,
                specialty = uiState.value.specialty,
                type = Type.ACTOR.name,
                weight = uiState.value.weight.toInt()
            )
        ).onSuccess { response ->
            if (response == null) {
                showToast("response is null")
                return@onSuccess
            }

            viewModelState.update {
                it.copy(actorProfileUiEvent = ActorProfileUiEvent.RegisterComplete)
            }
        }.onFail {
            showToast(it.message)
        }
    }

    override fun uploadProfileImages() = viewModelScope.launch {
        uploadImageUseCase(
            uiState.value.pictureList.map {
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
}

private class ActorProfileRegisterViewModelState(
    override val pictureList: List<String> = emptyList(),
    override val name: String = "",
    override val hookingComments: String = "",
    override val commentsTextLimit: Int = 50,
    override val birthday: String = "",
    override val gender: Gender? = Gender.IRRELEVANT,
    override val genderTagEnable: Boolean = false,
    override val height: String = "",
    override val weight: String = "",
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
    override val actorProfileUiEvent: ActorProfileUiEvent = ActorProfileUiEvent.Clear,
    override val focusEvent: ActorProfileFocusEvent? = null,
) : ActorProfileViewModelState() {
    override fun copy(
        pictureList: List<String>,
        name: String,
        hookingComments: String,
        commentsTextLimit: Int,
        birthday: String,
        gender: Gender?,
        genderTagEnable: Boolean,
        height: String,
        weight: String,
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
        actorProfileUiEvent: ActorProfileUiEvent,
        focusEvent: ActorProfileFocusEvent?,
    ): ActorProfileViewModelState =
        ActorProfileRegisterViewModelState(
            pictureList = pictureList,
            name = name,
            hookingComments = hookingComments,
            commentsTextLimit = commentsTextLimit,
            birthday = birthday,
            gender = gender,
            genderTagEnable = genderTagEnable,
            height = height,
            weight = weight,
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
            actorProfileUiEvent = actorProfileUiEvent,
            focusEvent = focusEvent,
        )
}
