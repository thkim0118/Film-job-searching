package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.imageupload.ImageUploadRequest
import com.fone.filmone.data.datamodel.request.imageupload.StageVariable
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.imageupload.ImageUploadRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val imageUploadRepository: ImageUploadRepository
) {
    suspend operator fun invoke(
        imageUrl: String,
        resource: String,
    ): DataResult<ImageUploadResponse> {
        return imageUploadRepository.uploadImage(
            // TODO 이미지 업로드 데이터
            ImageUploadRequest(
                "",
                "",
                StageVariable("")
            )
        )
    }
}