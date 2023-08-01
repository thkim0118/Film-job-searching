package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import com.fone.filmone.domain.model.common.DataResult

interface ImageUploadRepository {
    suspend fun uploadImage(
        uploadingImages: List<UploadingImage>
    ): DataResult<List<ImageUploadResponse>>
}