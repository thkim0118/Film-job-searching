package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeInquiryRequest
import com.fone.filmone.data.datamodel.fakeInquiryResponse
import com.fone.filmone.data.datamodel.fakeInquiryVo
import com.fone.filmone.data.datamodel.request.inquiry.InquiryRequest
import com.fone.filmone.data.datamodel.response.inquiry.InquiryResponse
import com.fone.filmone.data.datamodel.response.inquiry.Question
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.inquiry.InquiryType
import com.fone.filmone.domain.model.inquiry.InquiryVo
import com.fone.filmone.domain.repository.inquiry.InquiryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class SubmitInquiryUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val inquiryRepository = mock<InquiryRepository>()

    private val submitInquiryUseCase: SubmitInquiryUseCase by lazy {
        SubmitInquiryUseCase(inquiryRepository)
    }

    private lateinit var inquiryVo: InquiryVo
    private lateinit var inquiryRequest: InquiryRequest
    private lateinit var inquiryResponse: InquiryResponse

    @Before
    fun setUp() {
        inquiryVo = fakeInquiryVo
        inquiryRequest = fakeInquiryRequest
        inquiryResponse = fakeInquiryResponse
    }

    @Test
    fun submit_inquiry_success(): Unit = runBlocking {
        whenever(inquiryRepository.submitInquiry(inquiryRequest))
            .thenReturn(
                DataResult.Success(inquiryResponse)
            )

        submitInquiryUseCase(inquiryVo)
            .onSuccess {
                assert(true)
            }.onFail {
                assert(false)
            }
    }

    @Test
    fun submit_inquiry_fail(): Unit = runBlocking {
        whenever(inquiryRepository.submitInquiry(inquiryRequest))
            .thenReturn(
                DataResult.Fail(DataFail("", ""))
            )

        submitInquiryUseCase(inquiryVo)
            .onSuccess {
                assert(false)
            }.onFail {
                assert(true)
            }
    }
}