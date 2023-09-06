package com.infotech.rfid.utils

fun getFileExt(fileName: String): String? {
    return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
}