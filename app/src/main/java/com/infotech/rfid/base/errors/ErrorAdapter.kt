package com.infotech.rfid.base.errors

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.infotech.rfid.data.api.jstp.JSTPError
import java.lang.reflect.Type

class ErrorAdapter: JsonDeserializer<DataError> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): DataError? {
        var res = DataError(JSTPError.UNDEFINED)
        val err = json.asJsonObject.get("error")
        err?.let {
            if(!it.isJsonNull){
                it as JsonArray
                val code = it.get(0).asInt
                res = DataError(JSTPError.from(code))
            }
        }
        return res
    }
}