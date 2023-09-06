package com.infotech.rfid.data.api.jstp

import android.content.Context
import com.infotech.rfid.R
import com.infotech.rfid.base.errors.ErrorData

fun ErrorData.toMessage(context: Context): String?{
    return when(this.code){
        1304 -> context.getString(R.string.wrong_password)
        else -> null
    }
}