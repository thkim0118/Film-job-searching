package com.fone.filmone.data.datamodel.request.imageupload

data class ImageUploadRequest(
    val imageData: String,
    val resource: String,
    val stageVariables: StageVariable
)
