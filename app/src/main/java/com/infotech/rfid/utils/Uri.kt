package com.infotech.rfid.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore


fun Uri.toFileName(context: Context): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor =
        context.getContentResolver().query(this, projection, null, null, null) ?: return null
    val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    val s: String = cursor.getString(column_index)
    cursor.close()
    return s
}
fun Uri.toVideoFileName(context: Context): String? {
    val projection = arrayOf(MediaStore.Video.Media.DATA)
    val cursor: Cursor =
        context.getContentResolver().query(this, projection, null, null, null) ?: return null
    val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    val s: String = cursor.getString(column_index)
    cursor.close()
    return s
}

 fun Uri.getRealPath(context: Context): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Video.Media.DATA)
        cursor = context.getContentResolver().query(this, proj, null, null, null)
        cursor?.let {
            val column_index = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(column_index)
        }
    } catch (e: Exception) {
        this.path
    } finally {
        cursor?.close()
    }
}


fun Uri.findFileName(): String?{
    val list = this.pathSegments
    if(list.size > 0) {
        return list.get(list.size - 1)
    }else {
        return null
    }
}