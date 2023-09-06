package com.infotech.rfid.base.interfaces

import android.content.Intent

interface OnNewIntent {
    fun onNewIntent(intent: Intent?)
}