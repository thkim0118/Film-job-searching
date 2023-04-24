package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeCompetitionsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.competitions.CompetitionsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetCompetitionsUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val competitionsRepository = mock<CompetitionsRepository>()

    private val getCompetitionsUseCase by lazy {
        GetCompetitionsUseCase(competitionsRepository)
    }

    @Test
    fun get_competitions_success(): Unit = runBlocking {
        whenever(competitionsRepository.getCompetitions())
            .thenReturn(
                DataResult.Success(fakeCompetitionsResponse)
            )

        getCompetitionsUseCase()
            .onSuccess {
                assert(it?.competitions?.content?.firstOrNull()?.id == fakeCompetitionsResponse.competitions.content.first().id)
            }
            .onFail {
                assert(false)
            }
    }
}