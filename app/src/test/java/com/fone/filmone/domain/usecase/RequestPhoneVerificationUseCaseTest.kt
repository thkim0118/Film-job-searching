package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.response.sms.SmsTransmitResponse
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.SmsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class RequestPhoneVerificationUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val smsRepository = mock<SmsRepository>()

    private val requestPhoneVerificationUseCase by lazy {
        RequestPhoneVerificationUseCase(smsRepository)
    }

    @Test
    fun verification_code_is_correct(): Unit = runBlocking {
        val phoneNumber = "01012345678"
        val verificationCode = "123123"

        whenever(
            requestPhoneVerificationUseCase(
                phoneNumber = phoneNumber,
                verificationCode = verificationCode
            )
        ).thenReturn(
            DataResult.Success(
                data = SmsTransmitResponse("")
            )
        )

        requestPhoneVerificationUseCase(
            phoneNumber = phoneNumber,
            verificationCode = verificationCode
        ).onSuccess {
            assert(true)
        }.onFail {
            assert(false)
        }
    }

    @Test
    fun verification_code_is_not_correct(): Unit = runBlocking {
        val phoneNumber = "01012345678"
        val verificationCode = "123123"

        whenever(
            requestPhoneVerificationUseCase(
                phoneNumber = phoneNumber,
                verificationCode = verificationCode
            )
        ).thenReturn(
            DataResult.Success(
                data = SmsTransmitResponse("")
            )
        )

        requestPhoneVerificationUseCase(
            phoneNumber = phoneNumber,
            verificationCode = verificationCode
        ).onSuccess {
            assert(true)
        }.onFail {
            assert(false)
        }
    }

    @Test
    fun request_phone_verification_response_fail(): Unit = runBlocking {
        val phoneNumber = "01012345678"
        val verificationCode = "123123"

        whenever(
            requestPhoneVerificationUseCase(
                phoneNumber = phoneNumber,
                verificationCode = verificationCode
            )
        ).thenReturn(
            DataResult.Fail(
                dataFail = DataFail("", "")
            )
        )

        requestPhoneVerificationUseCase(
            phoneNumber = phoneNumber,
            verificationCode = verificationCode
        ).onSuccess {
            assert(false)
        }.onFail {
            assert(true)
        }
    }
}
