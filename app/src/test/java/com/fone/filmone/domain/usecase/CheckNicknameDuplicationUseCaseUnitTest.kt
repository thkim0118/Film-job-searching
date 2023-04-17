package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class CheckNicknameDuplicationUseCaseUnitTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepository = mock<UserRepository>()

    private val checkNicknameDuplicationUseCase by lazy {
        CheckNicknameDuplicationUseCase(userRepository)
    }

    @Test
    fun check_nickname_is_not_duplicated(): Unit = runBlocking {
        val testNickname = "test_nick_name"

        whenever(userRepository.checkNicknameDuplication(testNickname))
            .thenReturn(
                DataResult.Success(
                    CheckNicknameDuplicationResponse(
                        isDuplicate = false,
                        nickname = testNickname
                    )
                )
            )

        checkNicknameDuplicationUseCase(testNickname)
            .onSuccess {
                assert(it.not())
            }.onFail {
                assert(false)
            }
    }

    @Test
    fun check_nickname_is_duplicated(): Unit = runBlocking {
        val testNickname = "test_nick_name"

        whenever(userRepository.checkNicknameDuplication(testNickname))
            .thenReturn(
                DataResult.Success(
                    CheckNicknameDuplicationResponse(
                        isDuplicate = true,
                        nickname = testNickname
                    )
                )
            )

        checkNicknameDuplicationUseCase(testNickname)
            .onSuccess {
                assert(it)
            }.onFail {
                assert(false)
            }
    }

    @Test
    fun check_nickname_response_fail(): Unit = runBlocking {
        val testNickname = "test_nick_name"

        whenever(userRepository.checkNicknameDuplication(testNickname))
            .thenReturn(
                DataResult.Fail(
                    DataFail("", "")
                )
            )

        checkNicknameDuplicationUseCase(testNickname)
            .onSuccess {
                assert(false)
            }.onFail {
                assert(true)
            }
    }
}