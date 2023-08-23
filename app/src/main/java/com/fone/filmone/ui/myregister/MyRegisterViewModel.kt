package com.fone.filmone.ui.myregister

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.JobOpenings
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.profile.Profiles
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.domain.usecase.GetMyRegistrationJobOpeningsUseCase
import com.fone.filmone.domain.usecase.GetMyRegistrationProfileUseCase
import com.fone.filmone.domain.usecase.RemoveJobOpeningContentUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRegisterViewModel @Inject constructor(
    private val getMyRegistrationJobOpeningsUseCase: GetMyRegistrationJobOpeningsUseCase,
    private val getMyRegistrationProfileUseCase: GetMyRegistrationProfileUseCase,
    private val removeJobOpeningContentUseCase: RemoveJobOpeningContentUseCase,
) : BaseViewModel() {

    private val myRegisterViewModelState = MutableStateFlow(MyRegisterViewModelState())

    val uiState = myRegisterViewModelState
        .map(MyRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            myRegisterViewModelState.value.toUiState(),
        )

    private val _dialogState = MutableStateFlow<MyRegisterDialogState>(MyRegisterDialogState.Clear)
    val dialogState: StateFlow<MyRegisterDialogState> = _dialogState.asStateFlow()

    init {
        fetchMyRegisterContents()
    }

    fun fetchMyRegisterContents() = viewModelScope.launch {
        val jobOpeningsResponse =
            flowOf(getMyRegistrationJobOpeningsUseCase(page = 0).getOrNull())
        val profilesResponse = flowOf(getMyRegistrationProfileUseCase(page = 0).getOrNull())

        combine(jobOpeningsResponse, profilesResponse) { jobOpeningsResult, profilesResult ->
            myRegisterViewModelState.update {
                it.copy(
                    jobOpenings = jobOpeningsResult?.jobOpenings,
                    profiles = profilesResult?.profiles,
                )
            }
        }.stateIn(viewModelScope)
    }

    fun updateDialogState(dialogState: MyRegisterDialogState) {
        _dialogState.value = dialogState
    }

    fun removeRegisterContent(jobOpeningId: Int) = viewModelScope.launch {
        removeJobOpeningContentUseCase(jobOpeningId = jobOpeningId)
            .onSuccess {
                updateDialogState(MyRegisterDialogState.Clear)
                fetchMyRegisterContents()
            }.onFail {
                showToast(it.message)
            }
    }
}

private data class MyRegisterViewModelState(
    val jobOpenings: JobOpenings? = null,
    val profiles: Profiles? = null,
) {
    fun toUiState(): MyRegisterUiState = MyRegisterUiState(
        registerPosts = jobOpenings?.content?.map { content ->
            RegisterPostUiModel(
                id = content.id,
                type = content.type,
                categories = content.categories,
                title = content.title,
                deadline = if (content.deadline != null) content.deadline.replace(
                    "-",
                    ". "
                ) else R.string.always_recruiting.toString(),
                director = content.work.produce,
                gender = content.gender,
                period = content.dday,
                jobType = when (content.type) {
                    Type.ACTOR -> JobType.PART
                    Type.STAFF -> JobType.Field
                },
                casting = content.casting,
            )
        } ?: emptyList(),
        profilePosts = profiles?.content?.map { content ->
            RegisterPostProfileUiModel(
                profileUrl = content.profileUrl,
                name = content.name,
                type = content.type,
                info = makeUserAgeInfo(content.birthday, content.age),
            )
        } ?: emptyList(),
    )

    private fun makeUserAgeInfo(birthday: String, age: Int): String =
        "${birthday.split("-").firstOrNull() ?: ""}년생 (${age}살)"
}

data class MyRegisterUiState(
    val registerPosts: List<RegisterPostUiModel>,
    val profilePosts: List<RegisterPostProfileUiModel>,
)

data class RegisterPostUiModel(
    val id: Int,
    val type: Type,
    val categories: List<Category>,
    val title: String,
    val deadline: String,
    val director: String,
    val gender: Gender,
    val period: String,
    val jobType: JobType,
    val casting: String?,
)

data class RegisterPostProfileUiModel(
    val profileUrl: String,
    val name: String,
    val type: Type,
    val info: String,
)

sealed interface MyRegisterDialogState {
    object Clear : MyRegisterDialogState
    data class RegisterPostDelete(val profileId: Int) : MyRegisterDialogState
}
