package com.infotech.rfid.data.api.jstp

import com.google.gson.Gson
import com.infotech.rfid.base.annotations.DataType
import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.model.ProfileData
import com.infotech.rfid.data.model.UserInfoData

@DataType(ProfileData::class)
class ProfileHandler(gson: Gson, success:(ProfileData)->Unit, fail:(ErrorData)->Unit ): JSTPHandler<ProfileData>(gson, success, fail)
@DataType(UserInfoData::class)
class UserInfoHandler(gson: Gson, success:(UserInfoData)->Unit, fail:(ErrorData)->Unit ): JSTPHandler<UserInfoData>(gson, success, fail)
