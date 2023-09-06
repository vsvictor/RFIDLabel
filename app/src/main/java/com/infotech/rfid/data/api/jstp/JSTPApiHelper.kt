package com.infotech.rfid.data.api.jstp

import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.model.ProfileData
import com.infotech.rfid.data.model.UserInfoData

interface JSTPApiHelper {
    suspend fun login(login: String, password: String, success: (ProfileData) -> Unit, fail: (ErrorData) -> Unit):Nothing?
    suspend fun getUserInfo(success: (UserInfoData) -> Unit, fail: (ErrorData) -> Unit): Nothing?
}