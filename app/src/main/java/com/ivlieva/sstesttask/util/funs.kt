package com.ivlieva.sstesttask.util

import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast

fun showToast(message: String) {
    /* Функция показывает сообщение */
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun getFilenameFromUri(uri: Uri): String {
    var result = ""
    val cursor = APP_ACTIVITY.contentResolver.query(uri, null, null, null, null)
    try {
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
    } catch (e: Exception) {
        showToast(e.message.toString())
    } finally {
        cursor?.close()
        return result
    }
}

fun getUriFile(uri: Uri): Uri {
    return Uri.parse(uri.lastPathSegment?.let {
        uri.toString().replace(it.replace(":", "%3A"), getFilenameFromUri(uri), true)
    })
}