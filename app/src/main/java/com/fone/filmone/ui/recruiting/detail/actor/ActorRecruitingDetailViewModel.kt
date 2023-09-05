package com.fone.filmone.ui.recruiting.detail.actor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.response.jobopenings.detail.JobOpeningResponse
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetJobOpeningDetailUseCase
import com.fone.filmone.domain.usecase.RegisterScrapUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorRecruitingDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getJobOpeningDetailUseCase: GetJobOpeningDetailUseCase,
    private val scrapUseCase: RegisterScrapUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(ActorRecruitingDetailViewModelState())

    val uiState = viewModelState
        .map(ActorRecruitingDetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState(),
        )

    fun contact() {
        showToast(R.string.service)
    }

    fun scrapRecruiting() = viewModelScope.launch {
        val recruitingId: Int = uiState.value.id.toInt()
        scrapUseCase(recruitingId)
            .onSuccess {
                viewModelState.update { state ->
                    state.copy( // TODO: scrap 여부 반영 필요, 참고) ActorProfileDetailViewModel
                    )
                }
            }.onFail {
            }
    }

    init {
        viewModelScope.launch {
            val competitionId =
                savedStateHandle.get<Int>(FOneDestinations.ActorRecruitingDetail.argActorRecruitingDetail)
                    ?: return@launch

            getJobOpeningDetailUseCase(
                jobOpeningId = competitionId,
                type = Type.ACTOR,
            ).onSuccess { response ->
                if (response == null) {
                    showToast(R.string.toast_empty_data)
                    return@onSuccess
                }

                viewModelState.update {
                    it.copy(jobOpeningResponse = response)
                }
            }.onFail {
                showToast(it.message)
            }
        }
    }
}

private data class ActorRecruitingDetailViewModelState(
    val jobOpeningResponse: JobOpeningResponse? = null,
) {
    fun toUiState(): ActorRecruitingDetailUiState = if (jobOpeningResponse != null) {
        ActorRecruitingDetailUiState(
            id = jobOpeningResponse.jobOpening.id.toLong(),
            date = "", // TODO
            viewCount = String.format("%,d", jobOpeningResponse.jobOpening.viewCount),
            profileImageUrl = "", // TODO
            userNickname = "", // TODO
            userType = jobOpeningResponse.jobOpening.type.name,
            categories = jobOpeningResponse.jobOpening.categories,
            articleTitle = jobOpeningResponse.jobOpening.title,
            deadline = jobOpeningResponse.jobOpening.deadline ?: R.string.always_recruiting.toString(),
            dday = jobOpeningResponse.jobOpening.dday,
            casting = jobOpeningResponse.jobOpening.casting,
            numberOfRecruits = jobOpeningResponse.jobOpening.numberOfRecruits.toString(),
            ageRange = jobOpeningResponse.jobOpening.ageMin.toString() + jobOpeningResponse.jobOpening.ageMax.toString(),
            career = jobOpeningResponse.jobOpening.career.name,
            production = jobOpeningResponse.jobOpening.work.produce,
            workTitle = jobOpeningResponse.jobOpening.work.workTitle,
            director = jobOpeningResponse.jobOpening.work.director,
            genre = jobOpeningResponse.jobOpening.work.genre,
            logLine = jobOpeningResponse.jobOpening.work.logline ?: "",
            location = jobOpeningResponse.jobOpening.work.location ?: "",
            period = jobOpeningResponse.jobOpening.work.period ?: "",
            pay = jobOpeningResponse.jobOpening.work.pay ?: "",
            detailInfo = jobOpeningResponse.jobOpening.work.details,
            manager = jobOpeningResponse.jobOpening.work.manager,
            email = jobOpeningResponse.jobOpening.work.email,
        )
    } else {
        ActorRecruitingDetailUiState(
            id = 0L,
            date = "",
            viewCount = "",
            profileImageUrl = "",
            userNickname = "",
            userType = "",
            categories = emptyList(),
            articleTitle = "",
            deadline = "",
            dday = "",
            casting = "",
            numberOfRecruits = "",
            ageRange = "",
            career = "",
            production = "",
            workTitle = "",
            director = "",
            genre = "",
            logLine = "",
            location = "",
            period = "",
            pay = "",
            detailInfo = "",
            manager = "",
            email = "",
        )
    }
}

data class ActorRecruitingDetailUiState(
    val id: Long,
    val date: String,
    val viewCount: String,
    val profileImageUrl: String,
    val userNickname: String,
    val userType: String,
    val categories: List<Category>,
    val articleTitle: String,
    val deadline: String,
    val dday: String,
    val casting: String?,
    val numberOfRecruits: String,
    val ageRange: String,
    val career: String,
    val production: String,
    val workTitle: String,
    val director: String,
    val genre: String,
    val logLine: String,
    val location: String,
    val period: String,
    val pay: String,
    val detailInfo: String,
    val manager: String,
    val email: String,
)
