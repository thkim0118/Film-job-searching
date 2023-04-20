package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeUser
import com.fone.filmone.data.datamodel.fakeUserResponse
import com.fone.filmone.data.datamodel.fakeUserUpdateRequest
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.response.user.UserResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


internal class UpdateUserInfoUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepository = mock<UserRepository>()

    private val updateUserInfoUseCase by lazy { UpdateUserInfoUseCase(userRepository) }

    private lateinit var userUpdateRequest: UserUpdateRequest
    private lateinit var userResponse: UserResponse

    @Before
    fun setUp() {
        userUpdateRequest = fakeUserUpdateRequest
        userResponse = fakeUserResponse
    }

    @Test
    fun update_user_info_success(): Unit = runBlocking {
        whenever(userRepository.updateUserInfo(userUpdateRequest))
            .thenReturn(
                DataResult.Success(fakeUserResponse)
            )

        updateUserInfoUseCase(
            fakeUser.interests,
            fakeUser.job,
            fakeUser.nickname,
            fakeUser.profileUrl
        )
    }
}