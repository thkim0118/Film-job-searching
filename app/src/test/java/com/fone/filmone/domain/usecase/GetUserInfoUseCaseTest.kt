package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeUser
import com.fone.filmone.data.datamodel.response.user.*
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetUserInfoUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepository = mock<UserRepository>()

    private val getUserInfoUseCase by lazy {
        GetUserInfoUseCase(userRepository)
    }

    private lateinit var userResponse: UserResponse
    private lateinit var user: User

    @Before
    fun setUp() {
        user = fakeUser
        userResponse = UserResponse(
            user = user
        )
    }

    @Test
    fun get_user_info_success(): Unit = runBlocking {
        whenever(userRepository.getUserInfo())
            .thenReturn(
                DataResult.Success(userResponse)
            )

        getUserInfoUseCase()
            .onSuccess { response ->
                assert(response.user == user)
            }.onFail {
                assert(false)
            }
    }

    @Test
    fun get_user_info_fail(): Unit = runBlocking {
        whenever(userRepository.getUserInfo())
            .thenReturn(
                DataResult.Success(userResponse)
            )

        getUserInfoUseCase()
            .onSuccess { response ->
                assert(response.user.nickname != "fail_nick_name")
            }.onFail {
                assert(false)
            }
    }

}