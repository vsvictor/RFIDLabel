package com.infotech.rfid.data.repository

import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.model.*


class RepositoryImpl(private val local: LocalDataSource, private val remote: RemoteDataSource): Repository {
    override suspend fun login(login: String, password: String, success: (ProfileData) -> Unit, fail: (ErrorData) -> Unit):Nothing? = try { remote.login(login, password, success, fail) }catch (ex: Throwable){ throw ex }
    override suspend fun getUserInfo(success: (UserInfoData) -> Unit, fail: (ErrorData) -> Unit): Nothing? = try { remote.getUserInfo(success, fail) }catch (ex: Throwable){ throw ex }
}
