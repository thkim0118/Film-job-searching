package com.fone.filmone.ui.myregister

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.jobopenings.JobOpenings
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.profile.Profiles
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.domain.usecase.GetMyRegistrationJobOpeningsUseCase
import com.fone.filmone.domain.usecase.GetMyRegistrationProfileUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRegisterViewModel @Inject constructor(
    private val getMyRegistrationJobOpeningsUseCase: GetMyRegistrationJobOpeningsUseCase,
    private val getMyRegistrationProfileUseCase: GetMyRegistrationProfileUseCase
) : BaseViewModel() {

    private val myRegisterViewModelState = MutableStateFlow(MyRegisterViewModelState())

    val uiState = myRegisterViewModelState
        .map(MyRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            myRegisterViewModelState.value.toUiState()
        )

    private val _dialogState = MutableStateFlow<MyRegisterDialogState>(MyRegisterDialogState.Clear)
    val dialogState: StateFlow<MyRegisterDialogState> = _dialogState.asStateFlow()

    init {
        viewModelScope.launch {
            val jobOpeningsResponse =
                flowOf(getMyRegistrationJobOpeningsUseCase(page = 1).getOrNull())
            val profilesResponse = flowOf(getMyRegistrationProfileUseCase(page = 1).getOrNull())

            combine(jobOpeningsResponse, profilesResponse) { jobOpeningsResult, profilesResult ->
                MyRegisterViewModelState(
                    jobOpenings = jobOpeningsResult?.jobOpenings,
                    profiles = profilesResult?.profiles
                )
            }
        }
    }
}

private data class MyRegisterViewModelState(
    val jobOpenings: JobOpenings? = null,
    val profiles: Profiles? = null
) {
    fun toUiState(): MyRegisterUiState = MyRegisterUiState(
        registerPosts = jobOpenings?.jobOpening?.map { content ->
            RegisterPostUiModel(
                type = content.type,
                categories = content.categories,
                title = content.title,
                deadline = content.deadline,
                director = content.work.director,
                gender = content.gender,
                period = content.dday,
                jobType = JobType.Field, // TODO 변경
                casting = content.casting
            )
        } ?: emptyList(),
        profilePosts = profiles?.content?.map { content ->
            RegisterPostProfileUiModel(
                profileUrl = content.profileUrl,
                name = content.name,
                type = Type.ACTOR, // TODO type 값을 받아와야함.
                info = content.birthday + content.age
            )
        } ?: emptyList()
    )
}

data class MyRegisterUiState(
    val registerPosts: List<RegisterPostUiModel>,
    val profilePosts: List<RegisterPostProfileUiModel>
)

data class RegisterPostUiModel(
    val type: Type,
    val categories: List<Category>,
    val title: String,
    val deadline: String,
    val director: String,
    val gender: Gender,
    val period: String,
    val jobType: JobType,
    val casting: String,
)

data class RegisterPostProfileUiModel(
    val profileUrl: String,
    val name: String,
    val type: Type,
    val info: String
)

sealed interface MyRegisterDialogState {
    object Clear : MyRegisterDialogState
    object RegisterPostDelete : MyRegisterDialogState
}