package com.infotech.rfid.ui.login.data

import com.infotech.rfid.data.model.ProfileData


interface OnLogin {
    fun onLoggined(profile: ProfileData?)
    fun onNewPassword()
    fun onError()
}