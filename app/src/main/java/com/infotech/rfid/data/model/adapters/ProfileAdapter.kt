package com.infotech.rfid.data.model.adapters

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.infotech.rfid.data.model.ProfileData
import java.lang.reflect.Type

class ProfileAdapter: JsonDeserializer<ProfileData> {
    private val TAG= ProfileAdapter::class.java.simpleName
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): ProfileData? {
            var res: ProfileData? = null
            val ok = json.asJsonObject.get("ok")
            ok?.let {
                it as JsonArray
                val first = it.get(0)
                val userNameJson = first.asJsonObject.get("userName")
                userNameJson?.let {
                    if(!it.isJsonNull){
                         val arr = it.asString.split(" ")
                        try{
                            val firstName = arr.get(0)
                            val lastName = arr.get(1)
                            val middleName = arr.get(2)
                            res = ProfileData(firstName, lastName, middleName)
                        }catch (ex: IndexOutOfBoundsException){
                            val firstName = arr.get(0)
                            val lastName = try{
                                arr.get(1)
                            }catch (ex: IndexOutOfBoundsException){
                                ""
                            }
                            res = ProfileData(firstName, lastName)
                        }
                    }
                }
            }?: kotlin.run {
                res = null
            }
        return res
    }
}