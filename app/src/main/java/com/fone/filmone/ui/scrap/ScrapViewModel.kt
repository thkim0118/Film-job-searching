package com.fone.filmone.ui.scrap

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.response.common.jobopenings.Content
import com.fone.filmone.data.datamodel.response.common.jobopenings.JobOpenings
import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.common.jobopenings.Work
import com.fone.filmone.data.datamodel.response.common.paging.Pageable
import com.fone.filmone.data.datamodel.response.common.paging.Sort
import com.fone.filmone.data.datamodel.response.common.user.Career
import com.fone.filmone.data.datamodel.response.common.user.Category
import com.fone.filmone.data.datamodel.response.common.user.Domain
import com.fone.filmone.data.datamodel.response.common.user.Gender
import com.fone.filmone.data.datamodel.response.competition.CompetitionPrize
import com.fone.filmone.data.datamodel.response.competition.Competitions
import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.domain.usecase.GetCompetitionsScrapsUseCase
import com.fone.filmone.domain.usecase.GetJobOpeningsActorScrapsUseCase
import com.fone.filmone.domain.usecase.GetJobOpeningsStaffScrapsUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val getJobOpeningsActorScrapsUseCase: GetJobOpeningsActorScrapsUseCase,
    private val getJobOpeningsStaffScrapsUseCase: GetJobOpeningsStaffScrapsUseCase,
    private val getCompetitionsScrapsUseCase: GetCompetitionsScrapsUseCase,
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(ScarpViewModelState())

    val uiState = viewModelState
        .map(ScarpViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private fun getRandomInt(count: Int) = Random.nextInt(count)
    private fun getRandomCategory() = Category.values()[getRandomInt(Category.values().lastIndex)]
    private fun getRandomType() = Type.values()[getRandomInt(Type.values().lastIndex)]

    val fakeWork = Work(
        details = "details",
        director = "director",
        email = "test@test.com",
        genre = "genre",
        location = "location",
        logline = "logline",
        manager = "manager",
        pay = "pay",
        period = "period",
        produce = "produce",
        workTitle = "workTitle"
    )

    fun fakeJobContent() = Content(
        ageMax = 20,
        ageMin = 0,
        career = Career.IRRELEVANT,
        casting = "casting",
        categories = listOf(getRandomCategory(), getRandomCategory()),
        dday = "dday",
        deadline = "deadline",
        domains = listOf(Domain.ART),
        gender = Gender.MAN,
        id = 0,
        isScrap = true,
        numberOfRecruits = 0,
        scrapCount = 1,
        title = "title",
        type = getRandomType(),
        viewCount = 1,
        work = fakeWork
    )

    val fakeSort = Sort(
        empty = false,
        sorted = false,
        unsorted = false
    )

    val fakeJobOpenings = JobOpenings(
        content = listOf(
            fakeJobContent(),
            fakeJobContent(),
            fakeJobContent(),
            fakeJobContent(),
            fakeJobContent(),
            fakeJobContent(),
            fakeJobContent()
        ),
        empty = false,
        first = false,
        last = false,
        number = 1,
        numberOfElements = 1,
        pageable = Pageable(),
        size = 1,
        sort = fakeSort
    )

    val fakeCompetitionPrizes = CompetitionPrize(
        competitionId = 0,
        id = 0,
        prizeMoney = "prizeMoney",
        ranking = "ranking"
    )

    fun fakeCompetitionContent() = com.fone.filmone.data.datamodel.response.competition.Content(
        agency = "agency",
        competitionPrizes = listOf(fakeCompetitionPrizes),
        dday = "D-13",
        details = "details",
        endDate = "endDate",
        id = 0,
        imageUrl = "https://picsum.photos/200",
        isScrap = false,
        scrapCount = 0,
        showStartDate = "showStartDate",
        startDate = "startDate",
        submitEndDate = "submitEndDate",
        submitStartDate = "submitStartDate",
        title = "title",
        viewCount = 0
    )

    val fakeCompetition = Competitions(
        content = listOf(
            fakeCompetitionContent(),
            fakeCompetitionContent(),
            fakeCompetitionContent(),
            fakeCompetitionContent(),
            fakeCompetitionContent(),
            fakeCompetitionContent(),
            fakeCompetitionContent(),
            fakeCompetitionContent(),
            fakeCompetitionContent()
        ),
        empty = false,
        first = false,
        last = false,
        number = 0,
        numberOfElements = 0,
        pageable = Pageable(),
        size = 0,
        sort = fakeSort
    )

    val fakeCompetitionsResponse = CompetitionsResponse(
        competitions = fakeCompetition,
        totalCount = 1
    )

    init {
        viewModelScope.launch {
            val jobOpeningsActorResponse =
                flowOf(getJobOpeningsActorScrapsUseCase(page = 1).getOrNull())
            val jobOpeningsStaffResponse =
                flowOf(getJobOpeningsStaffScrapsUseCase(page = 1).getOrNull())
            val competitionsResponse = flowOf(getCompetitionsScrapsUseCase(page = 1).getOrNull())

            combine(
                jobOpeningsActorResponse,
                jobOpeningsStaffResponse,
                competitionsResponse
            ) { actorResult, staffResult, competitionsResult ->
                ScarpViewModelState(
                    actorJobOpenings = actorResult?.jobOpenings,
                    staffJobOpenings = staffResult?.jobOpenings,
                    competitionsResponse = competitionsResult
                )
            }.onEach { combinedViewModelState ->
                viewModelState.update { combinedViewModelState }
            }.launchIn(viewModelScope)
        }

//        viewModelState.update {
//            it.copy(
//                actorJobOpenings = fakeJobOpenings,
//                competitionsResponse = fakeCompetitionsResponse
//            )
//        }
    }
}

private data class ScarpViewModelState(
    val actorJobOpenings: JobOpenings? = null,
    val staffJobOpenings: JobOpenings? = null,
    val competitionsResponse: CompetitionsResponse? = null,
) {
    fun toUiState(): ScrapUiState {
        val actorContents = actorJobOpenings?.content ?: emptyList()
        val staffContents = staffJobOpenings?.content ?: emptyList()
        val combinedContents = (actorContents + staffContents).sortedBy { it.id }

        return ScrapUiState(
            jobOpenings = combinedContents.map { content ->
                JobOpeningUiModel(
                    type = content.type,
                    categories = content.categories,
                    title = content.title,
                    deadline = content.deadline,
                    director = content.work.director,
                    gender = content.gender,
                    period = content.dday,
//                    jobType = JobType.PART, // TODO 어떤 값을 써야하는지 찾아야함.
                    jobType = JobType.values()[Random.nextInt(JobType.values().lastIndex)],
                    casting = content.casting
                )
            } ?: emptyList(),
            competitions = competitionsResponse?.competitions?.content?.map { content ->
                CompetitionUiModel(
                    imageUrl = content.imageUrl,
                    title = content.title,
                    host = content.agency,
                    dday = content.dday,
                    vieweCount = content.viewCount.toString()
                )
            } ?: emptyList()
        )
    }
}

data class ScrapUiState(
    val jobOpenings: List<JobOpeningUiModel>,
    val competitions: List<CompetitionUiModel>
)

data class JobOpeningUiModel(
    val type: Type,
    val categories: List<Category>,
    val title: String,
    val deadline: String,
    val director: String,
    val gender: Gender,
    val period: String,
    val jobType: JobType,
    val casting: String
)

data class CompetitionUiModel(
    val imageUrl: String,
    val title: String,
    val host: String,
    val dday: String,
    val vieweCount: String
)