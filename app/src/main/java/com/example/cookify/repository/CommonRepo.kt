package com.example.cookify.repository

import android.content.Context
import android.net.Uri
import com.cloudinary.android.UploadContext

interface CommonRepo {
    fun uploadImage(context: Context,imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri) : String?

}