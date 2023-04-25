package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeMyJobOpeningsScrapResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.JobOpeningsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetMyJobOpeningsScrapsUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val jobOpeningsRepository = mock<JobOpeningsRepository>()

    private val getMyJobOpeningsScrapsUseCase by lazy {
        GetMyJobOpeningsScrapsUseCase(
            jobOpeningsRepository
        )
    }

    @Test
    fun get_my_job_openings_info_success(): Unit = runBlocking {
        whenever(jobOpeningsRepository.getMyJobOpeningsScraps())
            .thenReturn(
                DataResult.Success(fakeMyJobOpeningsScrapResponse)
            )

        getMyJobOpeningsScrapsUseCase()
            .onSuccess {
                assert(true)
            }.onFail {
                assert(false)
            }
    }
}