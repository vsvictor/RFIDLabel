package com.infotech.rfid.data.model.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.infotech.rfid.data.model.Entity
import java.lang.reflect.Type
import java.util.UUID

class EntityAdapter: JsonSerializer<Entity>, JsonDeserializer<Entity> {
    override fun serialize(src: Entity?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        src?.let {
            val res = JsonObject()
            res.addProperty("id", it.id.toString())
            res.addProperty("name", it.name)
            res.addProperty("comment", it.comment)
            return res
        }
        return null
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Entity? {
        json?.let {
            var id: UUID? = null
            val jsonID = it.asJsonObject.get("id")
            jsonID?.let {
                if(!it.isJsonNull){
                    id = UUID.fromString(it.asString)
                }
            }
            var name = ""
            val jsonName = it.asJsonObject.get("name")
            jsonName?.let {
                if(!it.isJsonNull){
                    name = it.asString
                }
            }
            var comment = ""
            val jsonComment = it.asJsonObject.get("comment")
            jsonComment?.let {
                if(!it.isJsonNull){
                    comment = it.asString
                }
            }
            return Entity(id!!, name, comment)
        }
        return null
    }
}