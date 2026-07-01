package com.grimco.gymapp.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.grimco.gymapp.domain.repository.ImageStorage
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import kotlin.uuid.Uuid

class AndroidStorage(private val context: Context): ImageStorage {
    override fun saveImage(name: String, bytes: ByteArray): String? {

        return try {
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            val file = File(context.filesDir, "${UUID.randomUUID()}.jpg")

            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.WEBP, 80, outputStream)
            }
            file.absolutePath
        }catch (e: Exception) {
            Log.e("ImageStorage", e.message ?: "imageStorage error")
            null
        }
    }
}