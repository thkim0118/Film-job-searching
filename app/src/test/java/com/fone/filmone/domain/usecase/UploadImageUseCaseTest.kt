package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeImageUploadResponse
import com.fone.filmone.data.datamodel.fakeUploadingImage
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.ImageUploadRepository
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

    private lateinit var uploadingImage: UploadingImage
    private lateinit var imageUploadResponse: ImageUploadResponse

    @Before
    fun setUp() {
        uploadingImage = fakeUploadingImage
        imageUploadResponse = fakeImageUploadResponse
    }

    @Test
    fun upload_image_success(): Unit = runBlocking {
        whenever(imageUploadRepository.uploadImage(uploadingImage))
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
        whenever(imageUploadRepository.uploadImage(uploadingImage))
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
