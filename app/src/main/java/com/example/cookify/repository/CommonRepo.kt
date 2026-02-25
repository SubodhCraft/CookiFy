package com.example.cookify.repository

import android.content.Context
import android.net.Uri

interface CommonRepo {
    fun uploadImage(context: Context, imageUri: Uri, callback: (Boolean, String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri) : String?

}