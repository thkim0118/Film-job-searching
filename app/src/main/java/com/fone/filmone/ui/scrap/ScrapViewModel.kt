package com.fone.filmone.ui.scrap

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import com.fone.filmone.data.datamodel.response.jobopenings.Category
import com.fone.filmone.data.datamodel.response.jobopenings.JobOpenings
import com.fone.filmone.data.datamodel.response.jobopenings.Type
import com.fone.filmone.data.datamodel.response.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(ScarpViewModelState())

    val uiState = viewModelState
        .map(ScarpViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )
}

private data class ScarpViewModelState(
    val jobOpenings: JobOpenings? = null,
    val competitionsResponse: CompetitionsResponse? = null,
) {
    fun toUiState(): ScrapUiState =
        ScrapUiState(
            jobOpenings = jobOpenings?.content?.map { content ->
                JobOpeningUiModel(
                    type = content.type,
                    categories = content.categories,
                    title = content.title,
                    deadline = content.title,
                    director = content.work.director,
                    gender = content.gender,
                    period = content.dday,
                    jobType = JobType.PART, // TODO 어떤 값을 써야하는지 찾아야함.
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

enum class JobType(@StringRes val stringRes: Int) {
    PART(R.string.job_opening_job_type_part_title),
    Field(R.string.job_opening_job_type_field_title)
}

data class CompetitionUiModel(
    val imageUrl: String,
    val title: String,
    val host: String,
    val dday: String,
    val vieweCount: String
)