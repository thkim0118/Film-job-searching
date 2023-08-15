package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeCompetitionsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.CompetitionsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetCompetitionsScrapsUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val competitionsRepository = mock<CompetitionsRepository>()

    private val getCompetitionsScrapsUseCase by lazy {
        GetCompetitionsScrapsUseCase(competitionsRepository)
    }

    @Test
    fun get_competitions_success(): Unit = runBlocking {
        whenever(competitionsRepository.getScraps(page = 1))
            .thenReturn(
                DataResult.Success(fakeCompetitionsResponse)
            )

        getCompetitionsScrapsUseCase(page = 1)
            .onSuccess {
                assert(it?.competitions?.competitionContent?.firstOrNull()?.id == fakeCompetitionsResponse.competitions.competitionContent.first().id)
            }
            .onFail {
                assert(false)
            }
    }
}
