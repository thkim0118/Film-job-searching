package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.request.imageupload.ImageUploadRequest
import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import com.fone.filmone.data.datasource.remote.ImageUploadApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ImageUploadRepository
import javax.inject.Inject

class ImageUploadRepositoryImpl @Inject constructor(
    private val imageUploadApi: ImageUploadApi
) : ImageUploadRepository {
    override suspend fun uploadImage(imageUploadRequest: ImageUploadRequest): DataResult<ImageUploadResponse> {
        return handleNetwork { imageUploadApi.uploadImage(imageUploadRequest) }
    }
}