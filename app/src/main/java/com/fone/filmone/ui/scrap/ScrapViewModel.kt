package com.fone.filmone.ui.scrap

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.domain.usecase.GetCompetitionsScrapsUseCase
import com.fone.filmone.domain.usecase.GetJobOpeningsActorScrapsUseCase
import com.fone.filmone.domain.usecase.GetJobOpeningsStaffScrapsUseCase
import com.fone.filmone.domain.usecase.RegisterScrapUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val getJobOpeningsActorScrapsUseCase: GetJobOpeningsActorScrapsUseCase,
    private val getJobOpeningsStaffScrapsUseCase: GetJobOpeningsStaffScrapsUseCase,
    private val getCompetitionsScrapsUseCase: GetCompetitionsScrapsUseCase,
    private val registerScrapUseCase: RegisterScrapUseCase,
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(ScarpViewModelState())

    val uiState = viewModelState
        .map(ScarpViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState(),
        )

    init {
        viewModelScope.launch {
            val jobOpeningsActorResponse =
                flowOf(getJobOpeningsActorScrapsUseCase(page = 0).getOrNull())
            val jobOpeningsStaffResponse =
                flowOf(getJobOpeningsStaffScrapsUseCase(page = 0).getOrNull())
            val competitionsResponse = flowOf(getCompetitionsScrapsUseCase(page = 0).getOrNull())

            combine(
                jobOpeningsActorResponse,
                jobOpeningsStaffResponse,
                competitionsResponse,
            ) { actorResult, staffResult, competitionsResult ->
                val actorContents = actorResult?.jobOpenings?.content ?: emptyList()
                val staffContents = staffResult?.jobOpenings?.content ?: emptyList()
                val combinedContents = (actorContents + staffContents).sortedBy { it.id }

                ScarpViewModelState(
                    scrapUiModel = ScrapUiModel(
                        jobOpenings = combinedContents.map { content ->
                            JobOpeningUiModel(
                                id = content.id,
                                type = content.type,
                                categories = content.categories,
                                title = content.title,
                                deadline = content.deadline,
                                director = content.work.director,
                                gender = content.gender,
                                period = content.dday,
                                jobType = when (content.type) {
                                    Type.ACTOR -> JobType.PART
                                    Type.STAFF -> JobType.Field
                                },
                                casting = content.casting,
                                isScrap = content.isScrap,
                            )
                        },
                        competitions = competitionsResult?.competitions?.content?.map { content ->
                            CompetitionUiModel(
                                id = content.id,
                                imageUrl = content.imageUrl,
                                title = content.title,
                                host = content.agency,
                                dday = content.dday,
                                vieweCount = content.viewCount.toString(),
                                isScrap = content.isScrap,
                            )
                        } ?: emptyList(),
                    )
                )
            }.onEach { combinedViewModelState ->
                viewModelState.update { combinedViewModelState }
            }.launchIn(viewModelScope)
        }
    }

    fun registerScrap(jobOpeningsId: Int) = viewModelScope.launch {
        registerScrapUseCase(jobOpeningsId)
            .onSuccess {
                viewModelState.update { state ->
                    state.copy(
                        scrapUiModel = state.scrapUiModel.copy(
                            jobOpenings = state.scrapUiModel.jobOpenings.map { uiModel ->
                                if (uiModel.id == jobOpeningsId) {
                                    uiModel.copy(isScrap = uiModel.isScrap.not())
                                } else {
                                    uiModel
                                }
                            }
                        ),
                    )
                }
            }
    }
}

private data class ScarpViewModelState(
    val scrapUiModel: ScrapUiModel = ScrapUiModel(
        jobOpenings = emptyList(),
        competitions = emptyList(),
    ),
) {
    fun toUiState(): ScrapUiModel {
        return scrapUiModel
    }
}

data class ScrapUiModel(
    val jobOpenings: List<JobOpeningUiModel>,
    val competitions: List<CompetitionUiModel>,
)

data class JobOpeningUiModel(
    val id: Int,
    val type: Type,
    val categories: List<Category>,
    val title: String,
    val deadline: String?,
    val director: String,
    val gender: Gender,
    val period: String,
    val jobType: JobType,
    val casting: String?,
    val isScrap: Boolean,
)

data class CompetitionUiModel(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val host: String,
    val dday: String,
    val vieweCount: String,
    val isScrap: Boolean,
)
