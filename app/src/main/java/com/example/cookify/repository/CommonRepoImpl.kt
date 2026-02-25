package com.example.cookify.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class CommonRepoImpl : CommonRepo {

    // --- SETUP INSTRUCTIONS ---
    // 1. Go to https://cloudinary.com and sign up for a free account.
    // 2. Go to your Dashboard and copy your 'Cloud Name', 'API Key', and 'API Secret'.
    // 3. Paste them below.
    private val config = mapOf(
        "cloud_name" to "dauwcs9v2",
        "api_key" to "493269643381974",
        "api_secret" to "FHGhTCJz2oIxOekq1g1H1P8NG1Q"
    )

    private var isInitialized = false

    private fun initCloudinary(context: Context) {
        if (!isInitialized) {
            try {
                MediaManager.init(context, config)
                isInitialized = true
            } catch (e: Exception) {
                // If already initialized, ignore
                isInitialized = true
            }
        }
    }

    override fun uploadImage(
        context: Context,
        imageUri: Uri,
        callback: (Boolean, String?) -> Unit
    ) {
        initCloudinary(context)
        
        val requestId = MediaManager.get().upload(imageUri)
            .unsigned("s3jqpwkl") 
            .callback(object : UploadCallback {
                override fun onStart(requestId: String) {}
                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}
                
                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                    val url = resultData["secure_url"] as? String
                    callback(true, url)
                }

                override fun onError(requestId: String, error: ErrorInfo) {
                    callback(false, error.description)
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {}
            })
            .dispatch()
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }
}
