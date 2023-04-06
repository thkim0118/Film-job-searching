package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.common.NetworkResponse
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import retrofit2.Response
import retrofit2.http.POST

interface ImageUploadApi {
    @POST
    suspend fun uploadImage(): Response<NetworkResponse<ImageUploadResponse>>
}