package com.fone.filmone.ui.main.job.recruiting.article.actor

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.response.jobopenings.detail.JobOpeningsDetailResponse
import com.fone.filmone.domain.usecase.GetJobOpeningDetailUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ActorRecruitingArticleViewModel @Inject constructor(
    private val getJobOpeningDetailUseCase: GetJobOpeningDetailUseCase,
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(ActorRecruitingArticleViewModelState())

    val uiState = viewModelState
        .map(ActorRecruitingArticleViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )


}

private data class ActorRecruitingArticleViewModelState(
    val jobOpeningsDetailResponse: JobOpeningsDetailResponse? = null
) {
    fun toUiState(): ActorRecruitingArticleUiState = if (jobOpeningsDetailResponse != null) {
        ActorRecruitingArticleUiState(
            date = "2022-10-11 15:45",// jobOpeningsDetailResponse.jobOpening.
            viewCount = String.format("%,d", jobOpeningsDetailResponse.jobOpening.viewCount),
            profileImageUrl = "", // TODO
            userNickname = "jobOpeningsDetailResponse.jobOpening",
            userType = jobOpeningsDetailResponse.jobOpening.type.name,
            categories = jobOpeningsDetailResponse.jobOpening.categories,
            title = jobOpeningsDetailResponse.jobOpening.title,
            deadline = jobOpeningsDetailResponse.jobOpening.deadline,
            dday = jobOpeningsDetailResponse.jobOpening.dday,
            actor = jobOpeningsDetailResponse.jobOpening.casting,
            personal = jobOpeningsDetailResponse.jobOpening.numberOfRecruits.toString(),
            ageRange = jobOpeningsDetailResponse.jobOpening.ageMin.toString() + jobOpeningsDetailResponse.jobOpening.ageMax.toString(),
            career = jobOpeningsDetailResponse.jobOpening.career.name,
            production = jobOpeningsDetailResponse.jobOpening.work.produce,
            recruitmentTitle = jobOpeningsDetailResponse.jobOpening.work.workTitle,
            director = jobOpeningsDetailResponse.jobOpening.work.director,
            genre = jobOpeningsDetailResponse.jobOpening.work.genre,
            logLine = jobOpeningsDetailResponse.jobOpening.work.logline,
            location = jobOpeningsDetailResponse.jobOpening.work.location,
            period = jobOpeningsDetailResponse.jobOpening.work.period,
            pay = jobOpeningsDetailResponse.jobOpening.work.pay,
            detailInfo = jobOpeningsDetailResponse.jobOpening.work.details,
            manager = jobOpeningsDetailResponse.jobOpening.work.manager,
            email = jobOpeningsDetailResponse.jobOpening.work.email
        )
    } else {
        ActorRecruitingArticleUiState(
            date = "",
            viewCount = "",
            profileImageUrl = "",
            userNickname = "",
            userType = "",
            categories = emptyList(),
            title = "",
            deadline = "",
            dday = "",
            actor = "",
            personal = "",
            ageRange = "",
            career = "",
            production = "",
            recruitmentTitle = "",
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

data class ActorRecruitingArticleUiState(
    val date: String,
    val viewCount: String,
    val profileImageUrl: String,
    val userNickname: String,
    val userType: String,
    val categories: List<Category>,
    val title: String,
    val deadline: String,
    val dday: String,
    val actor: String,
    val personal: String,
    val ageRange: String,
    val career: String,
    val production: String,
    val recruitmentTitle: String,
    val director: String,
    val genre: String,
    val logLine: String,
    val location: String,
    val period: String,
    val pay: String,
    val detailInfo: String,
    val manager: String,
    val email: String
)
