package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class LogoutUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepository = mock<UserRepository>()

    private val logoutUseCase by lazy {
        LogoutUseCase(userRepository)
    }

    @Test
    fun logout_success(): Unit = runBlocking {
        whenever(userRepository.logout())
            .thenReturn(
                DataResult.EmptyData
            )

        logoutUseCase()
            .onSuccess {
                assert(it == null)
            }.onFail {
                assert(false)
            }
    }
}
