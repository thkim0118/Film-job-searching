package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.response.user.SigninResponse
import com.fone.filmone.data.datamodel.response.user.Token
import com.fone.filmone.data.datamodel.response.user.User
import com.fone.filmone.domain.model.common.DataFail
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

internal class SignInUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepository = mock<UserRepository>()

    private val signInUseCase by lazy { SignInUseCase(userRepository) }

    private lateinit var signinRequest: SigninRequest
    private lateinit var signinResponse: SigninResponse

    @Before
    fun setUp() {
        signinRequest = SigninRequest(
            "accessToken",
            "email@email.com",
            "google"
        )
        signinResponse = SigninResponse(
            Token(
                accessToken = signinRequest.accessToken,
                expiresIn = 0,
                issuedAt = "",
                refreshToken = "",
                tokenType = ""
            ),
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
    fun sign_in_is_success(): Unit = runBlocking {
        whenever(userRepository.signIn(signinRequest))
            .thenReturn(
                DataResult.Success(signinResponse)
            )

        signInUseCase(
            signinRequest.accessToken,
            signinRequest.email,
            signinRequest.socialLoginType
        )
            .onSuccess {
                assert(true)
            }.onFail {
                assert(false)
            }
    }

    @Test
    fun sign_in_is_fail(): Unit = runBlocking {
        whenever(userRepository.signIn(signinRequest))
            .thenReturn(
                DataResult.Fail(dataFail = DataFail("", ""))
            )

        signInUseCase(
            signinRequest.accessToken,
            signinRequest.email,
            signinRequest.socialLoginType
        ).onSuccess {
            assert(false)
        }.onFail {
            assert(true)
        }
    }
}