package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImageRequest
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import com.fone.filmone.data.datasource.remote.ImageUploadApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ImageUploadRepository
import javax.inject.Inject

class ImageUploadRepositoryImpl @Inject constructor(
    private val imageUploadApi: ImageUploadApi
) : ImageUploadRepository {
    override suspend fun uploadImage(uploadingImages: List<UploadingImage>): DataResult<List<ImageUploadResponse>> {
        return handleNetwork { imageUploadApi.uploadImage(UploadingImageRequest(images = uploadingImages)) }
    }
}
