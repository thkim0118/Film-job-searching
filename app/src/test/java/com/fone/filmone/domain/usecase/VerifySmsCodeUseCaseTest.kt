package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.sms.SmsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class VerifySmsCodeUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val smsRepository = mock<SmsRepository>()

    private val verifySmsCodeUpUseCase by lazy {
        VerifySmsCodeUseCase(smsRepository)
    }

    @Test
    fun verify_sms_code_success(): Unit = runBlocking {
        val code = "123123"
        whenever(smsRepository.verifySmsVerificationCode(code))
            .thenReturn(
                DataResult.Success(true)
            )

        verifySmsCodeUpUseCase(code)
            .onSuccess {
                assert(it)
            }.onFail {
                assert(false)
            }
    }

    @Test
    fun verify_sms_code_fail(): Unit = runBlocking {
        val code = "123123"
        whenever(smsRepository.verifySmsVerificationCode(code))
            .thenReturn(
                DataResult.Success(false)
            )

        verifySmsCodeUpUseCase(code)
            .onSuccess {
                assert(it.not())
            }.onFail {
                assert(false)
            }
    }
}