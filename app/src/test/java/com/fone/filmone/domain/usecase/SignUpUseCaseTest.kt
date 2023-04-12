package com.fone.filmone.domain.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.response.user.SignUpResponse
import com.fone.filmone.data.datamodel.response.user.User
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.user.UserRepository
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.signup.model.SignUpVo.Companion.mapToSignUpRequest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class SignUpUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepository = mock<UserRepository>()

    private val signUpUseCase by lazy { SignUpUseCase(userRepository) }

    private lateinit var signUpRequest: SignUpRequest
    private lateinit var signUpResponse: SignUpResponse

    @Before
    fun setUp() {
        signUpRequest = SignUpRequest(
            accessToken = "",
            agreeToPersonalInformation = true,
            birthday = "",
            email = "",
            gender = "",
            interests = listOf(),
            isReceiveMarketing = true,
            job = "",
            nickname = "",
            phoneNumber = "",
            profileUrl = "",
            socialLoginType = ""
        )

        signUpResponse = SignUpResponse(
            User(
                agreeToPersonalInformation = true,
                agreeToTermsOfServiceTermsOfUse = true,
                birthday = "",
                email = "",
                enabled = true,
                gender = "",
                id = 0,
                interests = listOf(),
                isReceiveMarketing = true,
                job = "",
                nickname = "",
                phoneNumber = "",
                profileUrl = "",
                socialLoginType = ""
            )
        )
    }

    @Test
    fun sign_up_is_success(): Unit = runBlocking {
        whenever(userRepository.signUp(signUpRequest))
            .thenReturn(
                DataResult.Success(signUpResponse)
            )

        signUpUseCase.invoke(SignUpVo())
            .onSuccess {
                assert(true)
            }.onFail {
                assert(false)
            }
    }

    @Test
    fun sign_up_is_fail(): Unit = runBlocking {
        whenever(userRepository.signUp(signUpRequest))
            .thenReturn(DataResult.Fail(DataFail("", "")))

        signUpUseCase.invoke(SignUpVo())
            .onSuccess {
                assert(false)
            }.onFail {
                assert(true)
            }
    }
}