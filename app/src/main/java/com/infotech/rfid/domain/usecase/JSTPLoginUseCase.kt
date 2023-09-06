package com.infotech.mines.domain.usecase

import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.model.ProfileData
import com.infotech.rfid.data.repository.Repository
import com.infotech.rfid.domain.BaseUseCase


class JSTPLoginUseCase(val repo: Repository): BaseUseCase<ProfileData, JSTPLoginUseCase.Params>() {

    override suspend fun onLoad(params: Params, success: (ProfileData) -> Unit, fail: (ErrorData) -> Unit): Nothing? = repo.login(params.login, params.password, success, fail)

    data class Params(val login: String, val password: String, val newPassword: String? = null)
}