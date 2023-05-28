package com.fone.filmone.ui.main.job.recruiting.article.actor

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.response.jobopenings.detail.JobOpeningResponse
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
    val jobOpeningResponse: JobOpeningResponse? = null
) {
    fun toUiState(): ActorRecruitingArticleUiState = if (jobOpeningResponse != null) {
        ActorRecruitingArticleUiState(
            date = "2022-10-11 15:45",// jobOpeningsDetailResponse.jobOpening.
            viewCount = String.format("%,d", jobOpeningResponse.jobOpening.viewCount),
            profileImageUrl = "", // TODO
            userNickname = "jobOpeningsDetailResponse.jobOpening",
            userType = jobOpeningResponse.jobOpening.type.name,
            categories = jobOpeningResponse.jobOpening.categories,
            articleTitle = jobOpeningResponse.jobOpening.title,
            deadline = jobOpeningResponse.jobOpening.deadline,
            dday = jobOpeningResponse.jobOpening.dday,
            role = jobOpeningResponse.jobOpening.casting,
            numberOfRecruits = jobOpeningResponse.jobOpening.numberOfRecruits.toString(),
            ageRange = jobOpeningResponse.jobOpening.ageMin.toString() + jobOpeningResponse.jobOpening.ageMax.toString(),
            career = jobOpeningResponse.jobOpening.career.name,
            production = jobOpeningResponse.jobOpening.work.produce,
            workTitle = jobOpeningResponse.jobOpening.work.workTitle,
            director = jobOpeningResponse.jobOpening.work.director,
            genre = jobOpeningResponse.jobOpening.work.genre,
            logLine = jobOpeningResponse.jobOpening.work.logline,
            location = jobOpeningResponse.jobOpening.work.location,
            period = jobOpeningResponse.jobOpening.work.period,
            pay = jobOpeningResponse.jobOpening.work.pay,
            detailInfo = jobOpeningResponse.jobOpening.work.details,
            manager = jobOpeningResponse.jobOpening.work.manager,
            email = jobOpeningResponse.jobOpening.work.email
        )
    } else {
        ActorRecruitingArticleUiState(
            date = "",
            viewCount = "",
            profileImageUrl = "",
            userNickname = "",
            userType = "",
            categories = emptyList(),
            articleTitle = "",
            deadline = "",
            dday = "",
            role = "",
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

data class ActorRecruitingArticleUiState(
    val date: String,
    val viewCount: String,
    val profileImageUrl: String,
    val userNickname: String,
    val userType: String,
    val categories: List<Category>,
    val articleTitle: String,
    val deadline: String,
    val dday: String,
    val role: String,
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
    val email: String
)
