package com.fone.filmone.ui.myinfo

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.CheckNicknameDuplicationUseCase
import com.fone.filmone.domain.usecase.GetUserInfoUseCase
import com.fone.filmone.domain.usecase.UpdateUserInfoUseCase
import com.fone.filmone.domain.usecase.UploadImageUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val checkNicknameDuplicationUseCase: CheckNicknameDuplicationUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MyInfoUiState())
    val uiState: StateFlow<MyInfoUiState> = _uiState.asStateFlow()

    private val _dialogState = MutableStateFlow<MyInfoDialogState>(MyInfoDialogState.Clear)
    val dialogState: StateFlow<MyInfoDialogState> = _dialogState.asStateFlow()

    private lateinit var savedUserState: MyInfoUiState

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        getUserInfoUseCase()
            .onSuccess { userResponse ->
                val user = userResponse?.user ?: return@onSuccess
                savedUserState = MyInfoUiState(
                    profileUrl = user.profileUrl,
                    nickname = user.nickname,
                    job = user.job,
                    interests = user.interests
                )

                _uiState.update {
                    it.copy(
                        profileUrl = user.profileUrl,
                        nickname = user.nickname,
                        job = user.job,
                        interests = user.interests
                    )
                }
            }.onFail {
                showToast(it.message)
            }
    }

    fun updateDialog(dialogState: MyInfoDialogState) {
        _dialogState.value = dialogState
    }

    fun clearDialog() {
        _dialogState.value = MyInfoDialogState.Clear
    }

    fun updateProfileEncodingState() {
        _uiState.update {
            it.copy(
                isUpdateProfileEncoding = true
            )
        }
    }

    fun updateEncodedProfileString(encodedProfile: String) {
        _uiState.update {
            it.copy(
                encodedProfile = encodedProfile,
                isUpdateProfileEncoding = false
            )
        }

        updateEditButtonState()
    }

    fun updateDefaultProfile() {
        _uiState.update {
            it.copy(
                encodedProfile = null,
                profileUrl = null
            )
        }

        updateEditButtonState()
    }

    fun updateNickname(nickname: String) {
        _uiState.update {
            it.copy(
                nickname = nickname
            )
        }

        updateDuplicateState()
        updateEditButtonState()
    }

    fun checkNicknameDuplicate() = viewModelScope.launch {
        checkNicknameDuplicationUseCase(_uiState.value.nickname)
            .onSuccess {
                _uiState.update {
                    it.copy(
                        isEnableDuplicate = false
                    )
                }
            }.onFail {
                showToast(it.message)
            }

        updateEditButtonState()
    }

    fun updateJob(job: Job) {
        _uiState.update {
            it.copy(
                job = job
            )
        }

        updateEditButtonState()
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

        updateEditButtonState()
    }

    fun updateUserInfo(onSuccess: () -> Unit) = viewModelScope.launch {
        val encodedProfile: String? = _uiState.value.encodedProfile

        if (encodedProfile != null) {
            uploadImageUseCase(
                listOf(
                    UploadingImage(
                        imageData = encodedProfile,
                        resource = UploadImageUseCase.userProfileResource,
                        stageVariables = UploadImageUseCase.stageVariables
                    )
                )
            )
                .onSuccess {
                    updateUserInfoToRemote(
                        profileUrl = it?.firstOrNull()?.imageUrl ?: return@onSuccess,
                        onSuccess = onSuccess
                    )
                }.onFail {
                    showToast(R.string.toast_profile_register_fail)
                }
        } else {
            updateUserInfoToRemote(onSuccess = onSuccess)
        }
    }

    private fun updateUserInfoToRemote(
        profileUrl: String? = null,
        onSuccess: () -> Unit
    ) = viewModelScope.launch {
        val currentUiState = _uiState.value
        updateUserInfoUseCase(
            interests = currentUiState.interests,
            job = currentUiState.job ?: run {
                showToast(R.string.toast_empty_data)
                return@launch
            },
            nickname = currentUiState.nickname,
            profileUrl = profileUrl ?: currentUiState.profileUrl ?: "",
        ).onSuccess {
            onSuccess()
        }.onFail {
            showToast(it.message)
        }
    }

    private fun updateEditButtonState() {
        val currentState = _uiState.value

        fun isProfileModified() =
            currentState.profileUrl != savedUserState.profileUrl || currentState.encodedProfile != null

        fun isUserInfoModified() =
            currentState.job != savedUserState.job || currentState.interests.sorted() != savedUserState.interests.sorted() || currentState.nickname != savedUserState.nickname

        val enable =
            (isProfileModified() || isUserInfoModified()) &&
                currentState.nickname.isNotEmpty() &&
                currentState.interests.isNotEmpty() &&
                currentState.job != null

        _uiState.update {
            it.copy(
                isEnableEditButton = enable
            )
        }
    }

    private fun updateDuplicateState() {
        val currentState = _uiState.value
        val enable = currentState.nickname != savedUserState.nickname &&
            currentState.nickname.length >= 3

        _uiState.update {
            it.copy(
                isEnableDuplicate = enable
            )
        }
    }
}

data class MyInfoUiState(
    val isUpdateProfileEncoding: Boolean = false,
    val encodedProfile: String? = null,
    val profileUrl: String? = null,
    val nickname: String = "",
    val job: Job? = null,
    val interests: List<Category> = emptyList(),
    val isEnableEditButton: Boolean = false,
    val isEnableDuplicate: Boolean = false
)

sealed interface MyInfoDialogState {
    object ProfileSetting : MyInfoDialogState
    object Clear : MyInfoDialogState
}
