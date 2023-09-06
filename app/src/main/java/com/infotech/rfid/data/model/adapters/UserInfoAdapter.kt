package com.infotech.rfid.data.model.adapters

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.infotech.rfid.data.model.UserInfoData
import java.lang.reflect.Type

class UserInfoAdapter: JsonDeserializer<UserInfoData> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): UserInfoData? {
        var res: UserInfoData? = null
        json?.let {
            val ok = it.asJsonObject.get("ok")
            ok?.let {
                it as JsonArray
                val one = it.get(0)
                one?.let {
                    var crewID = ""
                    val jsonCrewID = it.asJsonObject.get("crewId")
                    jsonCrewID?.let {
                        if(!it.isJsonNull){
                            crewID = it.asString
                        }
                    }
                    var canBeHead = false
                    val jsonHead = it.asJsonObject.get("canBeHead")
                    jsonHead?.let {
                        if(!it.isJsonNull){
                            canBeHead = it.asBoolean
                        }
                    }
                    var userID = ""
                    val jsonUsrID = it.asJsonObject.get("userId")
                    jsonUsrID?.let {
                        if(!it.isJsonNull){
                            userID = it.asString
                        }
                    }
                    res = UserInfoData(crewID, canBeHead, userID)
                }

            }
        }
        return res
    }
}