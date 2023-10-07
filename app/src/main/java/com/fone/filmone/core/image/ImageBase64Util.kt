package com.fone.filmone.core.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageBase64Util {
    suspend fun encodeToString(context: Context, imageUri: Uri): String =
        withContext(Dispatchers.IO) {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            }

            bitmapToBase64String(bitmap)
        }

    suspend fun decodeToBitmap(base64String: String): Bitmap = withContext(Dispatchers.IO) {
        val encodeByte = Base64.decode(base64String, Base64.DEFAULT)

        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

    suspend fun loadBitmap(url: String): String? =
        withContext(Dispatchers.IO) {
            Log.d("Main", "url: $url")
            val connection = (URL(url).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 10000
                doInput = true
            }
            connection.connect()

            val encodedString = if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                bitmapToBase64String(BitmapFactory.decodeStream(connection.inputStream))
            } else {
                null
            }
            Log.d("Main", "encodedString: $encodedString")

            connection.disconnect()

            encodedString
        }

    private fun bitmapToBase64String(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
        byteArrayOutputStream.flush()
        byteArrayOutputStream.close()

        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
