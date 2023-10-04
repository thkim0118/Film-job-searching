package com.fone.filmone.ui.main.home

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.timer.HomeBannerTimer
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.response.home.HomeOrder
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetHomeItemsUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeItemsUseCase: GetHomeItemsUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(HomeViewModelState())

    val uiState = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private val bannerTimer = HomeBannerTimer(
        onTimeChanged = {
            viewModelState.update {
                it.copy(
                    homeUiEvent = it.homeUiEvent.copy(
                        currentBannerPage = it.homeUiEvent.currentBannerPage + 1
                    )
                )
            }
        }
    )

    fun getHome() {
        viewModelScope.launch {
            getHomeItemsUseCase().onSuccess { response ->
                if (response == null) {
                    return@onSuccess
                }

                viewModelState.update {
                    it.copy(
                        homeOrder = response.order,
                        recommendedJobOpening = RecommendedJobOpening(
                            title = response.jobOpening.title,
                            subtitle = response.jobOpening.subTitle,
                            recommendedContents = response.jobOpening.jobOpenings.content.map { content ->
                                RecommendedContent(
                                    id = content.id,
                                    type = content.type,
                                    title = content.title,
                                    subtitle = content.work.produce,
                                    dday = content.dday,
                                    tagStringResources = makeTagItems(
                                        content.gender,
                                        content.categories
                                    )
                                )
                            }
                        ),
//                        popularityCompetition = PopularityCompetition(
//                            title = response.competition.title,
//                            subtitle = response.competition.subTitle,
//                            popularityContents = response.competition.competitions.content.map { content ->
//                                PopularityContent(
//                                    id = content.id,
//                                    type = content.type,
//                                    imageUrl = content.imageUrl,
//                                    title = content.title,
//                                    dday = content.dday,
//                                    viewCount = String.format("%,d", content.viewCount)
//                                )
//                            }
//                        ),
                        homeActorProfile = HomeActorProfile(
                            title = response.profile.title,
                            subtitle = response.profile.subTitle,
                            homeActorProfileContents = response.profile.profile.content.map { content ->
                                HomeActorProfileContent(
                                    id = content.id,
                                    title = content.details,
                                    name = content.name,
                                    ageContent = makeAgeContent(content.birthday, content.age),
                                    gender = content.gender,
                                    imageUrl = content.profileUrl,
                                    hookingComment = content.hookingComment,
                                )
                            },
                        ),
                        homeUiEvent = HomeUiEvent(
                            currentBannerPage = getInitialPageCount(),
                            isBannerTouched = false
                        )
                    )
                }
            }
        }
    }

    init {
        getHome()
    }

    fun startTimer() {
        bannerTimer.startVerificationTimer()
    }

    fun stopTimer() {
        bannerTimer.finishVerificationTimer()
    }

    fun onUpdateBannerTouched() {
        stopTimer()

        viewModelState.update {
            it.copy(homeUiEvent = it.homeUiEvent.copy(isBannerTouched = true))
        }
    }

    fun onUpdatePageCount(pageCount: Int) {
        viewModelState.update {
            it.copy(
                homeUiEvent = it.homeUiEvent.copy(
                    currentBannerPage = pageCount,
                    isBannerTouched = false
                )
            )
        }
        startTimer()
    }

    override fun onCleared() {
        super.onCleared()

        bannerTimer.finishVerificationTimer()
    }

    private fun makeTagItems(gender: Gender, categories: List<Category>): List<Int> {
        return buildList {
            add(gender.stringRes)
            addAll(
                categories.sortedWith { c1, c2 ->
                    when {
                        c1 == Category.OTT_DRAMA -> 1
                        c2 == Category.OTT_DRAMA -> -1
                        else -> c1.ordinal.compareTo(c2.ordinal)
                    }
                }.map { it.stringRes }
            )
        }
    }

    private fun makeAgeContent(birthday: String, age: Int): String {
        return "${birthday.split('-').firstOrNull() ?: ""}년생 (${age}살)"
    }

    companion object {
        val bannerContents = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4,
            R.drawable.banner5,
            R.drawable.banner6,
        )

        fun getInitialPageCount(): Int {
            val halfPageCount = Int.MAX_VALUE / 2
            return halfPageCount - (halfPageCount % bannerContents.size)
        }
    }
}

private data class HomeViewModelState(
    val homeOrder: List<HomeOrder> = emptyList(),
    val recommendedJobOpening: RecommendedJobOpening = RecommendedJobOpening(
        title = "나와 비슷한 사람들이 보고있는 공고",
        subtitle = "",
        recommendedContents = emptyList(),
    ),
    val popularityCompetition: PopularityCompetition = PopularityCompetition(
        title = "인기 공모전",
        subtitle = "",
        popularityContents = emptyList()
    ),
    val homeActorProfile: HomeActorProfile = HomeActorProfile(
        title = "배우 프로필 보기",
        subtitle = "",
        homeActorProfileContents = emptyList()
    ),
    val homeUiEvent: HomeUiEvent = HomeUiEvent(
        currentBannerPage = HomeViewModel.getInitialPageCount(),
        isBannerTouched = false
    ),
) {
    fun toUiState(): HomeUiModel = HomeUiModel(
        homeOrder = homeOrder,
        recommendedJobOpening = recommendedJobOpening,
        popularityCompetition = popularityCompetition,
        homeActorProfile = homeActorProfile,
        homeUiEvent = homeUiEvent
    )
}

data class HomeUiModel(
    val homeOrder: List<HomeOrder>,
    val recommendedJobOpening: RecommendedJobOpening,
    val popularityCompetition: PopularityCompetition,
    val homeActorProfile: HomeActorProfile,
    val homeUiEvent: HomeUiEvent,
)

data class RecommendedJobOpening(
    val title: String,
    val subtitle: String,
    val recommendedContents: List<RecommendedContent>,
)

data class PopularityCompetition(
    val title: String,
    val subtitle: String,
    val popularityContents: List<PopularityContent>,
)

data class HomeActorProfile(
    val title: String,
    val subtitle: String,
    val homeActorProfileContents: List<HomeActorProfileContent>,
)

data class RecommendedContent(
    val id: Int,
    val type: Type,
    val title: String,
    val subtitle: String,
    val dday: String,
    val tagStringResources: List<Int>
)

data class PopularityContent(
    val id: Int,
    val type: Type,
    val imageUrl: String,
    val title: String,
    val dday: String,
    val viewCount: String
)

data class HomeActorProfileContent(
    val id: Int,
    val title: String,
    val name: String,
    val ageContent: String,
    val gender: Gender,
    val imageUrl: String,
    val hookingComment: String,
)

data class HomeUiEvent(
    val currentBannerPage: Int,
    val isBannerTouched: Boolean
)
