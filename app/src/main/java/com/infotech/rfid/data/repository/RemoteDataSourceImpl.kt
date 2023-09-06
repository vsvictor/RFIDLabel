package com.infotech.rfid.data.repository

import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.api.jstp.JSTPApiHelper
import com.infotech.rfid.data.api.rest.RestApiHelper
import com.infotech.rfid.data.model.*


class RemoteDataSourceImpl(private val restApiHelper: RestApiHelper, private val jstpApiHelper: JSTPApiHelper):RemoteDataSource {
    override suspend fun login(login: String, password: String, success: (ProfileData) -> Unit, fail: (ErrorData) -> Unit):Nothing? = jstpApiHelper.login(login, password, success, fail)
    override suspend fun getUserInfo(success: (UserInfoData) -> Unit, fail: (ErrorData) -> Unit): Nothing? = jstpApiHelper.getUserInfo(success, fail)
}