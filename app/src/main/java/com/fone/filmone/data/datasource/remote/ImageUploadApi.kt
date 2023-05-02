package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.request.imageupload.ImageUploadRequest
import com.fone.filmone.data.datamodel.response.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageUploadApi {
    @POST("prod/image-upload/user-profile")
    suspend fun uploadImage(
        @Body imageUploadRequest: ImageUploadRequest
    ): Response<NetworkResponse<ImageUploadResponse>>
}