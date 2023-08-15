package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImageRequest
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageUploadApi {
    @POST("prod/image-upload/user-profile")
    suspend fun uploadImage(
        @Body uploadingImageRequest: UploadingImageRequest
    ): Response<NetworkResponse<List<ImageUploadResponse>>>
}
