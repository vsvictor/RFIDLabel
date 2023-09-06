package com.infotech.rfid.data.api.jstp

import com.infotech.rfid.data.api.jstp.AbstractHandler

open class DataHandler<T>: AbstractHandler<T>(){

    open fun getParameterTypeName(): Class<*>? {
        try {
            val thisClass: Class<out DataHandler<*>?> = javaClass
            val modelField = thisClass.getField("model")
            val modelType = modelField.type
            //Class.forName(t1Type.typeName)
            return (Class.forName(modelType.canonicalName) as Class<T>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}