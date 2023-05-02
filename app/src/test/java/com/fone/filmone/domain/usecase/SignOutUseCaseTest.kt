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

internal class SignOutUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepository = mock<UserRepository>()

    private val signOutUseCase by lazy {
        SignOutUseCase(userRepository)
    }

    @Test
    fun signOut_success(): Unit = runBlocking {
        whenever(userRepository.signOut())
            .thenReturn(
                DataResult.EmptyData
            )

        signOutUseCase()
            .onSuccess {
                assert(it == null)
            }.onFail {
                assert(false)
            }
    }
}