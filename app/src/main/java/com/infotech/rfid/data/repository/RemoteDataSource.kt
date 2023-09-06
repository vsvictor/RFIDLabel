package com.infotech.rfid.data.repository

import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.base.errors.ExtException
import com.infotech.rfid.data.model.*


interface RemoteDataSource{
    @Throws(ExtException::class)
    suspend fun login(login: String, password: String, success: (ProfileData) -> Unit, fail: (ErrorData) -> Unit):Nothing?
    @Throws(ExtException::class)
    suspend fun getUserInfo(success: (UserInfoData) -> Unit, fail: (ErrorData) -> Unit): Nothing?
}
