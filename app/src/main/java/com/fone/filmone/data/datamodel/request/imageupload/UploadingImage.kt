package com.fone.filmone.data.datamodel.request.imageupload

import androidx.annotation.Keep

@Keep
data class UploadingImage(
    val imageData: String,
    val resource: String,
    val stageVariables: StageVariables
)
