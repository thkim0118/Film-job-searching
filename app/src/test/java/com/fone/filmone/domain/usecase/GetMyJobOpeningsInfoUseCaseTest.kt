package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeMyJobOpeningsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.jobopenings.JobOpeningsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetMyJobOpeningsInfoUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val jobOpeningsRepository = mock<JobOpeningsRepository>()

    private val getMyJobOpeningsInfoUseCase by lazy {
        GetMyJobOpeningsInfoUseCase(
            jobOpeningsRepository
        )
    }

    @Test
    fun get_my_job_openings_info_success(): Unit = runBlocking {
        whenever(jobOpeningsRepository.getMyJobOpeningsInfo())
            .thenReturn(
                DataResult.Success(fakeMyJobOpeningsResponse)
            )

        getMyJobOpeningsInfoUseCase()
            .onSuccess {
                assert(true)
            }.onFail {
                assert(false)
            }
    }
}