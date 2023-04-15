package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.imageupload.ImageUploadRequest
import com.fone.filmone.data.datamodel.request.imageupload.StageVariables
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.imageupload.ImageUploadRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val imageUploadRepository: ImageUploadRepository
) {
    suspend operator fun invoke(
        imageData: String,
        resource: String = "/image-upload/user-profile",
    ): DataResult<ImageUploadResponse> {
        return imageUploadRepository.uploadImage(
            ImageUploadRequest(
                imageData,
                resource,
                stageVariables = StageVariables(
                    stage = "prod"
                )
            )
        )
    }
}