package com.fone.filmone.data.datamodel.request.imageupload

import androidx.annotation.Keep

@Keep
data class UploadingImageRequest(
    val images: List<UploadingImage>
)
