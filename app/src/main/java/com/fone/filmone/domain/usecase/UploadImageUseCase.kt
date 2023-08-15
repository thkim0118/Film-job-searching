package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.imageupload.StageVariables
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ImageUploadRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val imageUploadRepository: ImageUploadRepository
) {
    suspend operator fun invoke(
        uploadingImages: List<UploadingImage>
    ): DataResult<List<ImageUploadResponse>> {
        return imageUploadRepository.uploadImage(uploadingImages)
    }

    companion object {
        const val userProfileResource = "/image-upload/user-profile"
        val stageVariables = StageVariables(
            stage = "prod"
        )
    }
}
