package com.infotech.mines.domain.usecase

import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.model.UserInfoData
import com.infotech.rfid.data.repository.Repository
import com.infotech.rfid.domain.BaseUseCase


class JSTPGetUserInfoUseCase(val repo: Repository): BaseUseCase<UserInfoData, Unit>() {

    override suspend fun onLoad(params: Unit, success: (UserInfoData) -> Unit, fail: (ErrorData) -> Unit): Nothing? = repo.getUserInfo(success, fail)


}