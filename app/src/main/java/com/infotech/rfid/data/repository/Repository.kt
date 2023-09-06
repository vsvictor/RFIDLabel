package com.infotech.rfid.data.repository

import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.model.*


interface Repository {
    suspend fun login(login: String, password: String, success: (ProfileData) -> Unit, fail: (ErrorData) -> Unit):Nothing?
    suspend fun getUserInfo(success: (UserInfoData) -> Unit, fail: (ErrorData) -> Unit): Nothing?
}