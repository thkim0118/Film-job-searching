package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.request.imageupload.ImageUploadRequest
import com.fone.filmone.data.datamodel.request.imageupload.StageVariables
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.imageupload.ImageUploadRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class UploadImageUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val imageUploadRepository = mock<ImageUploadRepository>()

    private val uploadImageUseCase by lazy {
        UploadImageUseCase(imageUploadRepository)
    }

    private lateinit var imageUploadRequest: ImageUploadRequest
    private lateinit var imageUploadResponse: ImageUploadResponse

    @Before
    fun setUp() {
        imageUploadRequest = ImageUploadRequest(
            imageData = "",
            resource = "/image-upload/user-profile",
            stageVariables = StageVariables(
                stage = "prod"
            )
        )
        imageUploadResponse = ImageUploadResponse("")
    }

    @Test
    fun upload_image_success(): Unit = runBlocking {
        whenever(imageUploadRepository.uploadImage(imageUploadRequest))
            .thenReturn(
                DataResult.Success(imageUploadResponse)
            )

        uploadImageUseCase("")
            .onSuccess {
                assert(true)
            }.onFail {
                assert(false)
            }
    }

    @Test
    fun upload_image_fail(): Unit = runBlocking {
        whenever(imageUploadRepository.uploadImage(imageUploadRequest))
            .thenReturn(
                DataResult.Fail(DataFail("", ""))
            )

        uploadImageUseCase("")
            .onSuccess {
                assert(false)
            }.onFail {
                assert(true)
            }
    }
}