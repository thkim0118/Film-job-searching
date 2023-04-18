package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.response.user.*
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.user.UserRepository
import com.fone.filmone.ui.signup.model.SignUpVo
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

    private lateinit var signUpVo: SignUpVo
    private lateinit var signUpRequest: SignUpRequest
    private lateinit var signUpResponse: SignUpResponse

    @Before
    fun setUp() {
        signUpVo = SignUpVo(
            accessToken = "accessToken",
            agreeToPersonalInformation = true,
            birthday = "birthday",
            email = "email",
            gender = "gender",
            interests = listOf(),
            isReceiveMarketing = true,
            job = "job",
            nickname = "nickname",
            phoneNumber = "phoneNumber",
            profileUrl = "profileUrl",
            socialLoginType = "socialLoginType",
            agreeToTermsOfServiceTermsOfUse = true
        )

        signUpRequest = SignUpRequest(
            accessToken = "accessToken",
            agreeToPersonalInformation = true,
            birthday = "birthday",
            email = "email",
            gender = "gender",
            interests = listOf(),
            isReceiveMarketing = true,
            job = "job",
            nickname = "nickname",
            phoneNumber = "phoneNumber",
            profileUrl = "profileUrl",
            socialLoginType = "socialLoginType",
            agreeToTermsOfServiceTermsOfUse = true
        )

        signUpResponse = SignUpResponse(
            User(
                agreeToPersonalInformation = true,
                agreeToTermsOfServiceTermsOfUse = true,
                birthday = "birthday",
                email = "email",
                enabled = true,
                gender = Gender.MAN,
                id = 0,
                interests = listOf(),
                isReceiveMarketing = true,
                job = Job.ACTOR,
                nickname = "nickname",
                phoneNumber = "phoneNumber",
                profileUrl = "profileUrl",
                socialLoginType = SocialLoginType.APPLE
            )
        )
    }

    @Test
    fun sign_up_is_success(): Unit = runBlocking {
        whenever(userRepository.signUp(signUpRequest))
            .thenReturn(
                DataResult.Success(signUpResponse)
            )

        signUpUseCase(signUpVo)
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

        signUpUseCase(signUpVo)
            .onSuccess {
                assert(false)
            }.onFail {
                assert(true)
            }
    }
}