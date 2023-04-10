package com.fone.filmone.core.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import kotlinx.coroutines.flow.flow
import java.io.ByteArrayOutputStream

object ImageBase64Util {
    suspend fun encodeToString(context: Context, imageUri: Uri) = flow {
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageUri)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        }

        val byteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

        val byteArray = byteArrayOutputStream.toByteArray()

        emit(Base64.encodeToString(byteArray, Base64.DEFAULT))
    }

    suspend fun decodeToBitmap(base64String: String) = flow {
        val encodeByte = Base64.decode(base64String, Base64.DEFAULT)

        emit(BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size))
    }
}