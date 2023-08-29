package com.fone.filmone.ui.profile.register.staff

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.data.datamodel.request.profile.ProfileRegisterRequest
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.RegisterProfileUseCase
import com.fone.filmone.domain.usecase.UploadImageUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffProfileRegisterViewModel @Inject constructor(
    private val registerProfileUseCase: RegisterProfileUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(StaffProfileRegisterViewModelState())

    val uiState = viewModelState
        .map(StaffProfileRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private val _dialogState =
        MutableStateFlow<StaffProfileRegisterDialogState>(StaffProfileRegisterDialogState.Clear)
    val dialogState: StateFlow<StaffProfileRegisterDialogState> = _dialogState.asStateFlow()

    fun registerProfile() {
        val state = viewModelState.value

        if (state.registerButtonEnable.not()) {
            return
        }

        val isValidationPassed = checkValidation()

        if (isValidationPassed) {
            uploadProfileImages()
        } else {
            showToast(R.string.toast_recruiting_register_fail)
            return
        }
    }

    private fun uploadProfileImages() = viewModelScope.launch {
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

    private fun register(imageUrls: List<String>) = viewModelScope.launch {
        registerProfileUseCase(
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
                type = Type.ACTOR.name,
                height = null,
                weight = null
            )
        ).onSuccess { response ->
            if (response == null) {
                showToast("response is null")
                return@onSuccess
            }

            viewModelState.update {
                it.copy(staffProfileRegisterUiEvent = StaffProfileRegisterUiEvent.RegisterComplete)
            }
        }
    }

    private fun checkValidation(): Boolean {
        val state = uiState.value

        if (state.pictureEncodedDataList.isEmpty()) {
            return false
        }

        if (state.name.isEmpty()) {
            return false
        }

        if (state.hookingComments.isEmpty()) {
            return false
        }

        if (state.birthday.isEmpty()) {
            return false
        }

        if (state.domains.isEmpty()) {
            return false
        }

        if (state.email.isEmpty()) {
            return false
        }

        if (state.detailInfo.isEmpty()) {
            return false
        }

        return true
    }

    fun updateDialogState(dialogState: StaffProfileRegisterDialogState) {
        _dialogState.value = dialogState
    }

    fun updateName(name: String) {
        viewModelState.update { state ->
            state.copy(name = name)
        }

        updateRegisterButtonState()
    }

    fun updateBirthday(birthday: String) {
        viewModelState.update { state ->
            state.copy(birthday = birthday)
        }

        updateRegisterButtonState()
    }

    fun updateGender(gender: Gender, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                gender = if (enable) {
                    gender
                } else {
                    null
                },
                genderTagEnable = enable.not()
            )
        }
    }

    fun updateGenderTag() {
        viewModelState.update { state ->
            state.copy(
                gender = null,
                genderTagEnable = true,
            )
        }
    }

    fun updateComments(comments: String) {
        viewModelState.update { state ->
            state.copy(hookingComments = comments)
        }

        updateRegisterButtonState()
    }

    fun updateRecruitmentDomains(domains: Set<Domain>) {
        viewModelState.update { state ->
            state.copy(
                domains = domains
            )
        }

        updateRegisterButtonState()
    }

    fun updateEmail(email: String) {
        viewModelState.update { state ->
            state.copy(email = email)
        }

        updateRegisterButtonState()
    }

    fun updateAbility(ability: String) {
        viewModelState.update { state ->
            state.copy(ability = ability)
        }
    }

    fun updateSns(sns: String) {
        viewModelState.update { state ->
            state.copy(sns = sns)
        }
    }

    fun updateDetailInfo(detailInfo: String) {
        viewModelState.update { state ->
            state.copy(detailInfo = detailInfo)
        }

        updateRegisterButtonState()
    }

    fun updateCareer(career: Career, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                career = if (enable) {
                    career
                } else {
                    null
                }
            )
        }
    }

    fun updateCareerDetail(careerDetail: String) {
        viewModelState.update { state ->
            state.copy(careerDetail = careerDetail)
        }
    }

    fun updateCategoryTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                categoryTagEnable = enable,
                categories = if (enable) {
                    Category.values().toSet()
                } else {
                    emptySet()
                }
            )
        }
    }

    fun updateCategory(category: Category, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                categories = if (enable) {
                    state.categories + setOf(category)
                } else {
                    state.categories.filterNot { it == category }.toSet()
                },
                categoryTagEnable = if (enable.not()) {
                    false
                } else if (enable && state.categories.size + 1 == Category.values().size) {
                    true
                } else {
                    state.categoryTagEnable
                }
            )
        }
    }

    fun updateImage(encodedString: String, remove: Boolean, limit: Int) {
        viewModelState.update { state ->
            state.copy(
                pictureList = if (remove) {
                    val copyList = state.pictureList.toMutableList().apply {
                        remove(encodedString)
                    }

                    copyList
                } else if (state.pictureList.size < 9) {
                    state.pictureList + encodedString
                } else {
                    state.pictureList
                }
            )
        }
    }

    private fun updateRegisterButtonState() {
        val modelState = viewModelState.value

        val isEnable = modelState.name.isNotEmpty() && modelState.hookingComments.isNotEmpty() &&
            modelState.birthday.isNotEmpty() && PatternUtil.isValidDate(modelState.birthday) && modelState.domains.isNotEmpty() &&
            modelState.email.isNotEmpty() && PatternUtil.isValidEmail(modelState.email) && modelState.detailInfo.isNotEmpty()

        viewModelState.update { state ->
            state.copy(registerButtonEnable = isEnable)
        }
    }
}

private data class StaffProfileRegisterViewModelState(
    val pictureList: List<String> = emptyList(),
    val name: String = "",
    val hookingComments: String = "",
    val commentsTextLimit: Int = 50,
    val birthday: String = "",
    val gender: Gender? = Gender.IRRELEVANT,
    val genderTagEnable: Boolean = true,
    val domains: Set<Domain> = emptySet(),
    val email: String = "",
    val ability: String = "",
    val sns: String = "",
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 200,
    val career: Career? = null,
    val careerDetail: String = "",
    val careerDetailTextLimit: Int = 500,
    val categories: Set<Category> = emptySet(),
    val categoryTagEnable: Boolean = false,
    val registerButtonEnable: Boolean = false,
    val staffProfileRegisterUiEvent: StaffProfileRegisterUiEvent = StaffProfileRegisterUiEvent.Clear
) {
    fun toUiState(): StaffProfileRegisterUiModel = StaffProfileRegisterUiModel(
        pictureEncodedDataList = pictureList,
        name = name,
        hookingComments = hookingComments,
        commentsTextLimit = commentsTextLimit,
        birthday = birthday,
        gender = gender,
        genderTagEnable = genderTagEnable,
        domains = domains,
        email = email,
        specialty = ability,
        sns = sns,
        detailInfo = detailInfo,
        detailInfoTextLimit = detailInfoTextLimit,
        career = career,
        careerDetail = careerDetail,
        careerDetailTextLimit = careerDetailTextLimit,
        categories = categories,
        categoryTagEnable = categoryTagEnable,
        registerButtonEnable = registerButtonEnable,
        staffProfileRegisterUiEvent = staffProfileRegisterUiEvent
    )
}

data class StaffProfileRegisterUiModel(
    val pictureEncodedDataList: List<String>,
    val name: String,
    val hookingComments: String,
    val commentsTextLimit: Int,
    val birthday: String,
    val gender: Gender?,
    val genderTagEnable: Boolean = true,
    val domains: Set<Domain>,
    val email: String,
    val specialty: String,
    val sns: String,
    val detailInfo: String,
    val detailInfoTextLimit: Int,
    val career: Career?,
    val careerDetail: String,
    val careerDetailTextLimit: Int,
    val categories: Set<Category>,
    val categoryTagEnable: Boolean,
    val registerButtonEnable: Boolean,
    val staffProfileRegisterUiEvent: StaffProfileRegisterUiEvent,
)

sealed class StaffProfileRegisterDialogState {
    object Clear : StaffProfileRegisterDialogState()
    data class DomainSelectDialog(
        val selectedDomains: List<Domain>
    ) : StaffProfileRegisterDialogState()
}

sealed class StaffProfileRegisterUiEvent {
    object RegisterComplete : StaffProfileRegisterUiEvent()
    object Clear : StaffProfileRegisterUiEvent()
}
