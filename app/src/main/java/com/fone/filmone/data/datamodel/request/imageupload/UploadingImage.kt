package com.fone.filmone.data.datamodel.request.imageupload

data class UploadingImage(
    val imageData: String,
    val resource: String,
    val stageVariables: StageVariables
)
