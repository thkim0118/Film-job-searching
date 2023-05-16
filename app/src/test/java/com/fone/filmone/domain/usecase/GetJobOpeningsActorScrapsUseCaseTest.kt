package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeJobOpeningsScrapResponse
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.JobOpeningsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetJobOpeningsActorScrapsUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val jobOpeningsRepository = mock<JobOpeningsRepository>()

    private val getJobOpeningsActorScrapsUseCase by lazy {
        GetJobOpeningsActorScrapsUseCase(
            jobOpeningsRepository
        )
    }

    @Test
    fun get_my_job_openings_info_success(): Unit = runBlocking {
        whenever(jobOpeningsRepository.getJobOpeningsScraps(page = 1, type = Type.ACTOR))
            .thenReturn(
                DataResult.Success(fakeJobOpeningsScrapResponse)
            )

        getJobOpeningsActorScrapsUseCase(page = 1)
            .onSuccess {
                assert(true)
            }.onFail {
                assert(false)
            }
    }
}